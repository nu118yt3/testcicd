# Guía de Estudio Definitiva para Entrevista Técnica DevOps 2025  
**Herramientas incluidas:** GitHub • Kubernetes • Docker • Ansible • Jenkins • Prometheus • Terraform  
Versión corregida y mejorada – diciembre 2025 – Formato limpio y listo para imprimir o usar en Notion/Obsidian

---

## 1. GitHub & Git

### Definición y conceptos clave
Plataforma basada en Git para control de versiones, colaboración y automatización CI/CD.

**Características más preguntadas**
- GitHub Actions (workflows CI/CD)
- Secrets + Secret Scanning + Dependabot
- Branch protection rules + CODEOWNERS
- GitHub Advanced Security

### Preguntas típicas de entrevista
- Cómo evitar fugas de secretos
- Flujo completo de GitHub Actions
- Diferencia API REST v3 vs GraphQL v4
- Required status checks y pull request approvals

### Escenarios
- ¿Cómo evitas que se filtren secretos en el repositorio? → Usar GitHub Secrets + Secret Scanning + nunca hacer commit de credenciales.
- Explica un flujo completo de CI/CD con GitHub Actions (trigger → runner → jobs → steps → actions).
- ¿Qué pasa si usas la API GraphQL (v4) vs REST (v3)? → Algunas funcionalidades aún solo están en v3.
- ¿Cómo proteger una rama main/master? → Branch protection rules + required status checks + CODEOWNERS.
- Diferencia entre fork, branch y pull request.

### Comandos Git esenciales (con explicación)

```bash
git clone https://github.com/user/repo.git          # Clona el repositorio
git checkout -b feature/nueva                     # Crea y cambia a nueva rama
git add .                                           # Añade todos los cambios al staging
git commit -m "feat: descripción clara"             # Commit con mensaje convencional
git push origin feature/nueva                       # Sube la rama al remoto
git pull --rebase origin main                       # Actualiza y rebasea tu rama
git push --force-with-lease                         # Fuerza push de forma segura
```

---

## 2. Kubernetes (K8s)

### Definición y conceptos clave
Orquestador de contenedores open-source (CNCF). Automatiza despliegue, escalado y operación.

**Objetos principales:** Pod → Deployment → Service → Ingress → ConfigMap/Secret → Namespace → API server, etcd, kubelet, controller-manager, scheduler

### Preguntas típicas de entrevista
- Diferencia Deployment vs StatefulSet vs DaemonSet
- Persistencia → PV → StorageClass → PVC
- Troubleshooting de pods (CrashLoopBackOff, ImagePullBackOff, OOMKilled)
- Liveness, Readiness y Startup probes
- Estrategias de rollout (RollingUpdate vs Blue/Green)

### Escenarios
- Explica la diferencia entre Pod, ReplicaSet, Deployment y StatefulSet.
- ¿Cómo manejas almacenamiento persistente en Kubernetes? → PersistentVolume (PV) → StorageClass → PersistentVolumeClaim (PVC).
- Troubleshooting típico: un Pod está en CrashLoopBackOff, ¿qué haces? → kubectl logs, kubectl describe pod, kubectl exec.
- ¿Cómo expones una aplicación? → Service (ClusterIP, NodePort, LoadBalancer) + Ingress.
- Concepto de liveness/readiness probes y por qué son críticos.

### Comandos kubectl más usados (con descripción)

```bash
kubectl get pods -n ns --watch                      # Lista pods con actualización en tiempo real
kubectl describe pod nombre-pod -n ns               # Información detallada + eventos
kubectl logs nombre-pod -c contenedor --tail=100 -f # Logs en vivo
kubectl exec -it nombre-pod -- sh                   # Acceso interactivo al contenedor
kubectl apply -f manifiesto.yaml                    # Crea/actualiza recursos (declarativo)
kubectl create namespace produccion                 # Crea un namespace
kubectl rollout status deployment/mi-app            # Estado del rollout
kubectl rollout undo deployment/mi-app --to-revision=2 # Revierte a versión anterior
```

---

## 3. Docker

### Definición y conceptos clave
Motor de contenedorización que empaqueta aplicación + dependencias.
Plataforma de contenedorización que empaqueta aplicaciones y sus dependencias en imágenes ligeras y portables.

**Conceptos críticos:** Imagen (inmutable) vs Contenedor (instancia), Dockerfile, multi-stage builds, Imagen vs Contenedor, Dockerfile, Docker Compose, Docker Hub.

### Preguntas típicas
- Instrucciones Dockerfile más importantes
- Cómo reducir tamaño de imágenes
- CMD vs ENTRYPOINT
- Volúmenes vs bind mounts vs tmpfs

### Escenarios
- Explica las instrucciones más importantes de un Dockerfile (FROM, RUN, COPY, CMD, ENTRYPOINT, EXPOSE).
- ¿Cómo reduces el tamaño de una imagen Docker? → Multi-stage builds, .dockerignore, alpine base.
- Diferencia entre CMD y ENTRYPOINT.
- ¿Cómo se integra Docker en una pipeline Jenkins? → Docker Pipeline plugin o docker inside stage.
- ¿Qué es un volumen y por qué no usar /tmp para datos persistentes?

### Comandos Docker esenciales

```bash
docker build -t mi-app:v1.2.0 .                     # Construye imagen desde Dockerfile
docker images                                       # Lista imágenes locales
docker run -d --name web -p 8080:80 mi-app:v1.2.0   # Ejecuta contenedor en background
docker exec -it web sh                              # Entra al contenedor en ejecución
docker logs -f web                                  # Ver logs en tiempo real
docker volume create datos-db                       # Crea volumen persistente
docker push usuario/mi-app:v1.2.0                   # Sube imagen a registry
docker system prune -a --volumes                    # Limpieza agresiva (cuidado)
```

---

## 4. Ansible

### Definición y conceptos clave
Herramienta de automatización agentless e idempotente basada en YAML.

**Componentes clave:** Playbooks, Inventory, Roles, Variables, Vault

### Preguntas estrella de entrevista
- Orden exacto de precedencia de variables (¡pregunta obligatoria!)
- Gestión segura de secretos → Ansible Vault
- Molecule para testing de roles
- Escalabilidad (100 k+ nodos)

### Escenarios
- Orden de precedencia de variables (¡pregunta estrella!):
--extra-vars → mayor precedencia → role vars → inventory vars → group_vars/all → role defaults (menor).
- ¿Cómo proteges contraseñas y claves? → Ansible Vault (--vault-password-file recomendado).
- Diferencia entre ansible (ad-hoc) y ansible-playbook.
- ¿Cómo pruebas roles? → Molecule + Docker/Vagrant.
- Escalabilidad: Ansible puede manejar +100k nodos (modo pull con Ansible Tower/AWX).

### Comandos Ansible esenciales

```bash
ansible-playbook -i inventory.yml site.yml                    # Ejecuta playbook principal
ansible-playbook site.yml --extra-vars "env=prod ver=2.5"     # Sobrescribe variables (máxima prioridad)
ansible-playbook site.yml --vault-password-file ~/.vault_pass # Usa Vault sin prompt
ansible all -m ping                                           # Prueba conectividad
ansible webservers -m package -a "name=nginx state=present"   # Comando ad-hoc
ansible-vault encrypt secrets.yml                             # Encripta archivo
ansible-vault view secrets.yml                                # Muestra archivo encriptado
```

---

## 5. Jenkins CI/CD

### Definición y conceptos clave
Orquestador líder de CI/CD. Arquitectura Controller + Agents.

**Conceptos críticos**
- Pipeline as Code → Jenkinsfile
- Declarative (recomendado) vs Scripted Pipeline
- Shared Libraries
- Jenkins Configuration as Code (JCasC)

### Preguntas típicas
- Diferencias Declarative vs Scripted
- Reutilización con Shared Libraries
- Despliegue moderno (Helm + JCasC + agentes en K8s)
- Seguridad (RBAC)

### Escenarios
- Cómo reutilizas código entre pipelines? → Shared Libraries (@Library('mi-lib') _).
- ¿Cómo despliegas Jenkins en producción hoy? → Helm chart oficial + JCasC + agentes en Kubernetes.
- Blue-Green ocean vs Classic UI.
- Seguridad: Role-Based Strategy plugin, Matrix Authorization.

### Comandos útiles

```bash
# Jenkins CLI (requiere java -jar jenkins-cli.jar)
java -jar jenkins-cli.jar -s http://jenkins:8080 who-am-i
java -jar jenkins-cli.jar build mi-pipeline -s -v
java -jar jenkins-cli.jar console mi-pipeline #123

# Despliegue con Helm (recomendado 2025)
helm repo add jenkinsci https://charts.jenkins.io
helm upgrade --install jenkins jenkinsci/jenkins -n jenkins -f values-prod.yaml
```

---

## 6. Prometheus + Alertmanager + Grafana

### Definición y conceptos clave
Sistema de monitoreo basado en series temporales con modelo pull.

**Componentes clave:** Prometheus server(TSDB + PromQL), Alertmanager(gestión de alertas), Exporters(node_exporter, mysql_exporter, etc.), PromQL, Grafana(visualización)

### Preguntas típicas
- Funciones PromQL: rate(), irate(), increase(), predict_linear()
- Configuración de alertas + Alertmanager (grouping, inhibition, silencing)
- Alta disponibilidad → Thanos o Cortex/VictoriaMetrics
- Federación y remote write/read

### Escenarios
- Explica PromQL: consultas instantáneas vs range, rate(), irate(), increase().
- ¿Cómo configuras alertas? → rules.yml + Alertmanager (routing, inhibition, silencing).
- ¿Prometheus tiene alta disponibilidad nativa? → No, se usa Thanos o Cortex/VictoriaMetrics para HA y retención larga.
- Troubleshooting: “scrape target down” o “context deadline exceeded”.
- Federacion vs Remote Write/Read.

### Comandos esenciales

```bash
promtool check config /etc/prometheus/prometheus.yml      # Valida configuración
promtool check rules /etc/prometheus/rules/*.yml          # Valida reglas de alerta
curl -X POST http://localhost:9090/-/reload               # Recarga config sin reiniciar
journalctl -u prometheus -f                               # Logs en tiempo real (systemd)
promtool query instant 'up' http://localhost:9090         # Prueba rápida de conectividad
```

---

## 7. Terraform (IaC)

### Definición y conceptos clave
Herramienta declarativa de Infraestructura como Código (HashiCorp).

**Componentes críticos:** State, Providers, Modules, Backends, Workspaces
- Estado (state): archivo terraform.tfstate que guarda el estado real de la infraestructura.
- Providers: plugins que interactúan con las APIs de los proveedores (AWS, Azure, kubernetes, helm, etc.).
- Modules: forma de reutilizar y organizar código Terraform.
- Backend: dónde se almacena el state (local, S3, Terraform Cloud, Consul, Azure Blob, etc.).
- Workspaces: entornos aislados (dev, staging, prod) con el mismo código.

### Preguntas estrella
- Gestión segura del tfstate (remote backend + locking + encryption)
- Flujo completo: init → validate → plan → apply
- Manejo de secretos (sensitive vars + Vault integration)
- Drift detection y terraform import
- Workspaces vs directorios separados vs Terraform Cloud

### Escenarios
Gestión segura del estado (state)
Nunca guardar el tfstate en el repositorio Git.
Usar remote backend con locking (S3 + DynamoDB, Terraform Cloud, Azure, etc.).
Habilitar state encryption y versionado.

¿Qué pasa cuando ejecutas los comandos en este orden?terraform init → terraform plan → terraform apply → terraform destroy
(Pregunta casi garantizada para evaluar si realmente has usado Terraform en producción).
Diferencia entre terraform plan y terraform apply -auto-approve
plan solo muestra qué va a hacer (dry-run).
apply ejecuta los cambios; con -auto-approve salta la confirmación manual (peligroso en producción).

¿Cómo manejas secretos en Terraform?
Nunca hardcodear credenciales.
Mejores prácticas: variables sensibles marcadas como sensitive = true, uso de HashiCorp Vault, AWS Secrets Manager, Azure Key Vault, Terraform Cloud variables encriptadas.

Drift detection y problemas comunes
¿Qué haces si alguien cambió manualmente un recurso en la consola de AWS? → terraform plan detecta drift, luego terraform apply lo vuelve a alinear o terraform import si es nuevo.
Errores típicos: “resource already exists”, “inconsistent dependency lock file”, “state lock”.

Estrategias de despliegue en producción
Uso de workspaces vs directorios separados.
Terraform Cloud / Enterprise con runs remotos, policy as code (Sentinel/OPA), VCS-driven workflows.

### Comandos Terraform más usados

```bash
# Inicialización y configuración
terraform init                     # descarga providers y módulos
terraform init -upgrade            # actualiza providers a últimas versiones compatibles
terraform fmt -recursive           # formatea todo el código HCL
terraform validate                 # valida sintaxis y configuración

# Planificación y revisión
terraform plan                     # muestra qué cambios se van a hacer
terraform plan -out=tfplan.out     # guarda el plan en archivo (buena práctica)
terraform show -json tfplan.out    # útil para pipelines y policy checks

# Aplicación
terraform apply                    # ejecuta con confirmación
terraform apply -auto-approve      # sin confirmación (CI/CD)
terraform apply tfplan.out         # aplica un plan guardado previamente

# Gestión del estado
terraform state list
terraform state show aws_instance.mi_servidor
terraform import aws_instance.nuevo i-1234567890abcdef0
terraform refresh                  # actualiza el state sin cambiar infraestructura

# Limpieza y destrucción
terraform destroy                  # destruye todo el stack
terraform destroy -target=aws_instance.web  # destruye solo un recurso

# Workspaces
terraform workspace new prod
terraform workspace select staging
terraform workspace list

# Otros útiles
terraform output                   # muestra outputs definidos
terraform output -json             # formato JSON (muy usado en CI)
terraform taint aws_instance.old   # fuerza recreación en el siguiente apply
```
Preguntas trampa habituales de Terraform

¿Qué pasa si borras el archivo terraform.tfstate? → Pierdes el mapeo → caos total.
¿Por qué count y for_each son mejores que múltiples bloques idénticos? → DRY + mejor manejo de cambios.
¿Puedes usar Terraform para gestionar recursos de Kubernetes? → Sí, con los providers kubernetes, helm y kubectl.
---
