resource "google_container_node_pool" "application_nodepool" {
  name       = var.np_name
  location   = var.location
  cluster    = var.cluster_name
  node_count = var.node_count

  node_config {
    image_type   = var.image_type
    machine_type = var.machine_type
    disk_size_gb = var.disk_size_gb

    oauth_scopes = [
    "https://www.googleapis.com/auth/compute",
    "https://www.googleapis.com/auth/devstorage.read_only",
    "https://www.googleapis.com/auth/logging.write",
    "https://www.googleapis.com/auth/monitoring",
    ]
  }
} 

