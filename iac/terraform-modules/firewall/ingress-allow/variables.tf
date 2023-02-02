variable "name" {
  type        = string
  description = "The name of the firewall rule"
}

variable "description" {
  type        = string
  description = "The description of the firewall rule"
}

variable "network" {
  type        = string
  description = "The network this firewall rule applies to"
  default     = "default"
}

variable "priority" {
  type        = string
  description = "The firewall rule priority"
  default     = "1000"
}

variable "protocol" {
  type        = string
  description = "The name of the protocol to allow"
  default     = "tcp"
}

variable "ports" {
  type        = list(string)
  description = "A list of ports and/or port ranges to allow"
}

variable "source_ranges" {
  type        = list(string)
  description = "A list of source CIDR ranges that this firewall applies to"
  default     = ["165.243.5.25/32"]
}

variable "source_tags" {
  type        = list(string)
  description = "A list of source tags for this firewall rule"
  default     = []
}

variable "target_tags" {
  type        = list(string)
  description = "A list of target tags for this firewall rule"
  default     = []
}
