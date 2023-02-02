PROJECT_NAME=$(gcloud config get-value project)
find ./ -name "*.yml" -exec sed -i "s/$PROJECT_NAME/_PROJECT_/g" {} \;
find ./ -name "*.tfvars" -exec sed -i "s/$PROJECT_NAME/_PROJECT_/g" {} \;
find ./ -name "*.tf" -exec sed -i "s/$PROJECT_NAME/_PROJECT_/g" {} \;
