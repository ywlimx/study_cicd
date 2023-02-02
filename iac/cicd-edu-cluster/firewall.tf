# define firewall rules here
# use firewall/ingress_allow module to allow incoming traffic

module "fw_ssh" {
  source      = "../terraform-modules/firewall/ingress-allow"
  name        = var.fw_name
  description = "Allow SSH for everyone"
  network     = google_compute_network.ntw.name
  protocol    = "tcp"
  ports       = ["22"]
}
