# create network to run cluster instances
resource "google_compute_network" "ntw" {
  name                    = var.ntw_name
  routing_mode            = var.routing_mode
  auto_create_subnetworks = false
}

# create cluster to run cluster instances
resource "google_compute_subnetwork" "subnet" {
  name                     = var.subnet_name
  ip_cidr_range            = var.ip_cidr_range
  region                   = var.region
  network                  = google_compute_network.ntw.self_link
  description              = var.description
  private_ip_google_access = true

  secondary_ip_range {
    range_name    = var.pod_subnet_name
    ip_cidr_range = var.pod_subnet_cidr
  }
  secondary_ip_range {
    range_name    = var.service_subnet_name
    ip_cidr_range = var.service_subnet_cidr
  }
}

# create k8s cluster
module "cicd_edu_cluster" {
  source                  = "../terraform-modules/cluster"
  name                    = var.name
  description             = var.description
  location                = var.location
  initial_node_count      = var.initial_node_count
  cluster_network         = google_compute_network.ntw.name
  cluster_subnet          = google_compute_subnetwork.subnet.name
  cluster_sec_range_name  = var.pod_subnet_name
  services_sec_range_name = var.service_subnet_name
}

# create k8s application node pool and attach it to cicd_edu_cluster
module "application_node_pool" {
  source       = "../terraform-modules/node-pool"
  np_name      = var.np_name
  location     = var.location
  node_count   = var.node_count
  cluster_name = module.cicd_edu_cluster.name
}

// resource "null_resource" "get_k8s_credential" {
//   depends_on = [module.application_node_pool.name]
//   provisioner "local-exec" {
//     command = "gcloud container clusters get-credentials cicd-edu-cluster"
//   }
// }