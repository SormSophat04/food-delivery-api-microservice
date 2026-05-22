{{- define "infra.namespace" -}}
{{- .Values.global.namespace | default "food-delivery-system" -}}
{{- end -}}
