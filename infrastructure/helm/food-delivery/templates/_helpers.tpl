{{- define "food-delivery.namespace" -}}
{{- .Values.global.namespace | default "food-delivery-system" -}}
{{- end -}}

{{- define "food-delivery.image" -}}
{{- $registry := .Values.global.imageRegistry | default "food-delivery" -}}
{{- $tag := .Values.global.imageTag | default "v1" -}}
{{- $image := .image | default .name -}}
{{- printf "%s/%s:%s" $registry $image $tag -}}
{{- end -}}

{{- define "food-delivery.labels" -}}
{{- $name := .name | default .Chart.Name -}}
app: {{ $name }}
chart: {{ $.Chart.Name }}-{{ $.Chart.Version }}
release: {{ $.Release.Name }}
heritage: {{ $.Release.Service }}
{{- end -}}

{{- define "food-delivery.postgres.env" -}}
- name: SPRING_DATASOURCE_URL
  value: jdbc:postgresql://{{ .Values.infrastructure.postgres.host }}:{{ .Values.infrastructure.postgres.port }}/{{ .Values.infrastructure.postgres.database }}
- name: SPRING_DATASOURCE_USERNAME
  value: {{ .Values.infrastructure.postgres.username }}
- name: SPRING_DATASOURCE_PASSWORD
  value: {{ .Values.infrastructure.postgres.password }}
{{- end -}}

{{- define "food-delivery.eureka.env" -}}
{{- $eureka := index .Values.services "eureka-server" -}}
- name: EUREKA_CLIENT_SERVICEURL_DEFAULTZONE
  value: http://{{ $eureka.name }}:{{ $eureka.port }}/eureka/
{{- end -}}

{{- define "food-delivery.config.env" -}}
{{- $config := index .Values.services "config-server" -}}
- name: SPRING_CLOUD_CONFIG_URI
  value: http://{{ $config.name }}:{{ $config.port }}
{{- end -}}
