resource "google_compute_network" "ntw" {
  name                    = var.ntw_name
  description             = var.description
  routing_mode            = var.routing_mode
  auto_create_subnetworks = false
}


resource "google_compute_subnetwork" "subnet" {
  name          = var.subnet_name
  ip_cidr_range = var.ip_cidr_range
  region        = var.region
  network       = google_compute_network.ntw.self_link
  private_ip_google_access = true
}