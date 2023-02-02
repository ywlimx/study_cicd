output "application_node_pool" {
  value = module.cicd_edu_cluster.name
}

output "kubconfig" {
  value = " ... \nRun command to configure access via kubectl:\n$ gcloud container clusters get-credentials ${module.cicd_edu_cluster.name} --zone ${var.zone} --project ${var.project_id}"
}
