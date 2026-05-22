{{- define "common.namespace" -}}
{{- .Values.global.namespace | default "food-delivery-system" -}}
{{- end -}}

{{- define "common.image" -}}
{{- $registry := .Values.global.imageRegistry | default "food-delivery" -}}
{{- $tag := .Values.global.imageTag | default "v1" -}}
{{- printf "%s/%s:%s" $registry .Values.image.repository $tag -}}
{{- end -}}
