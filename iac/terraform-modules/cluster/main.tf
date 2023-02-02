resource "google_container_cluster" "primary" {
  name               = var.name
  location           = var.location
  description        = var.description

  network            = var.cluster_network
  subnetwork         = var.cluster_subnet

  remove_default_node_pool = var.remove_default_node_pool
  initial_node_count = var.initial_node_count

  ip_allocation_policy {
    cluster_secondary_range_name  = var.cluster_sec_range_name
    services_secondary_range_name = var.services_sec_range_name
  }
  
  # master_auth {
  #   username = ""
  #   password = ""

  #   client_certificate_config {
  #     issue_client_certificate = false
  #   }
  # }
  # addons_config {
  #   http_load_balancing {
  #     disabled = var.disable_autoscaling_addon
  #   }
  # }
  
  # network    = var.network
  # subnetwork = var.subnetwork
  
  # # node configuration
  # # NOTE: nodes created during the cluster creation become the default node pool
  # node_config {
  #   image_type   = var.node_image_type
  #   machine_type = var.node_machine_type
  #   disk_size_gb = var.node_disk_size_gb

  #   # The set of Google API scopes
  #   # The following scopes are necessary to ensure the correct functioning of the cluster
  #   oauth_scopes = [
  #     "https://www.googleapis.com/auth/compute",
  #     "https://www.googleapis.com/auth/devstorage.read_only",
  #     "https://www.googleapis.com/auth/logging.write",
  #     "https://www.googleapis.com/auth/monitoring",
  #   ]

  #   # Tags can used to identify targets in firewall rules
  #   tags = ["${var.name}-cluster", "nodes"]
  # }
}
