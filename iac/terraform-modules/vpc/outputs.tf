output "name" {
  value = "${google_compute_network.ntw.name}"
}

output "subnet_name" {
  value = "${google_compute_subnetwork.subnet.name}"
}
