variable "name" {
  type        = string
  description = "Network name"
}

variable "description" {
  type        = string
  description = "Network description"
  default     = "cicd-edu cluster network"
}

variable "routing_mode" {
  type        = string
  description = "VPC Routing mode"
  default     = "REGIONAL"
}

variable "region" {
  type        = string
  description = "Region in which to manage GCP resources"
}

variable "ip_cidr_range" {
  type        = string
  description = "Subnet CIDR IP Range"
  default     = "10.204.0.0/24"
}
