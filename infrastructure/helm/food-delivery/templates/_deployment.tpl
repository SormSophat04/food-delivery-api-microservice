{{- define "food-delivery.service-deployment" -}}
{{- $service := .service -}}
{{- $ctx := .ctx -}}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ $service.name }}
  namespace: {{ include "food-delivery.namespace" $ctx }}
spec:
  replicas: {{ $service.replicas | default 1 }}
  selector:
    matchLabels:
      app: {{ $service.name }}
  template:
    metadata:
      labels:
        app: {{ $service.name }}
    spec:
      containers:
        - name: {{ $service.name }}
          image: "{{ $ctx.Values.global.imageRegistry }}/{{ $service.image }}:{{ $ctx.Values.global.imageTag }}"
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: {{ $service.port }}
          env:
{{- if $service.usesPostgres }}
{{ include "food-delivery.postgres.env" $ctx | indent 12 }}
{{- end }}
{{- if $service.usesMongo }}
            - name: SPRING_DATA_MONGODB_URI
              value: "mongodb://{{ $ctx.Values.infrastructure.mongodb.host }}:{{ $ctx.Values.infrastructure.mongodb.port }}/food_delivery"
{{- end }}
{{- if $service.usesKafka }}
            - name: SPRING_KAFKA_BOOTSTRAP_SERVERS
              value: "{{ $ctx.Values.infrastructure.kafka.name }}:{{ $ctx.Values.infrastructure.kafka.port }}"
{{- end }}
{{ include "food-delivery.eureka.env" $ctx | indent 12 }}
{{ include "food-delivery.config.env" $ctx | indent 12 }}
{{- if $service.extraEnv }}
{{ toYaml $service.extraEnv | indent 12 }}
{{- end }}
{{- end -}}

{{- define "food-delivery.service" -}}
{{- $service := .service -}}
{{- $ctx := .ctx -}}
{{ include "food-delivery.service-deployment" . }}
---
apiVersion: v1
kind: Service
metadata:
  name: {{ $service.name }}
  namespace: {{ include "food-delivery.namespace" $ctx }}
spec:
  selector:
    app: {{ $service.name }}
  ports:
    - port: {{ $service.externalPort | default $service.port }}
      targetPort: {{ $service.port }}
  type: {{ $service.serviceType | default "ClusterIP" }}
{{- end -}}
