variable "name" {
  type        = string
  description = "The name of the cluster, unique within the project and zone"
  default     = "default"
}

variable "description" {
  type        = string
  description = "Cluster description"
}

variable "location" {
  type        = string
  description = "The zone the master and nodes specified in initial_node_count should be created in"
}

variable "disable_dashboard" {
  type        = bool
  description = "Whether the Kubernetes Dashboard should be disabled"
  default     = false
}

variable "disable_autoscaling_addon" {
  type        = bool
  description = "Whetherthe Autoscaling Pod addon should be disabled"
  default     = false
}

variable "remove_default_node_pool" {
  type        = bool
  description = "remove default node pool if not using default node pool"
  default     = true
}

variable "initial_node_count" {
  type        = number
  description = "The number of nodes to create in this cluster (not including the Kubernetes master)(if remove_default_node_pool is true, should be set to 1)"
  default     = 1
}

variable "network" {
  type        = string
  description = "The name or self_link of the Google Compute Engine network to which the cluster is connected"
  default     = "default"
}

variable "subnetwork" {
  type        = string
  description = "The name or self_link of the Google Compute Engine subnetwork to which the cluster is connected"
  default     = "default"
}

variable "node_disk_size_gb" {
  type        = number
  description = "Size of the disk attached to each node, specified in GB"
  default     = 10
}

variable "node_machine_type" {
  type        = string
  description = "The name of a Google Compute Engine machine type"
  default     = "n1-standard-1"
}

variable "node_image_type" {
  type        = string
  description = "The image type to use for nodes. See supported image types https://cloud.google.com/kubernetes-engine/docs/concepts/node-images"
  default     = "COS" # Container-Optimized OS
}

variable "cluster_network" {
  type = string
}

variable "cluster_subnet" {
  type = string
}

variable "cluster_sec_range_name" {
  type = string
}

variable "services_sec_range_name" {
  type = string
}
