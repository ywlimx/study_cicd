terraform {
  backend "gcs" {
    credentials = "/practice/credentials/_PROJECT_.json" # for bucket Access
    bucket      = "_PROJECT_-bucket"
  }
}
