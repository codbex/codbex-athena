# codbex-athena

Accounting Management Application


## Run Athena Docker image locally

### Prerequisites
```shell
export GIT_REPO_FOLDER='<path-to-athena-git-repo>'
export GH_TOKEN='<your-token>'

export IMAGE='ghcr.io/codbex/codbex-athena:dev'
export CONTAINER_NAME='athena'
```

### Build Docker image

```shell
# download packages
cd "$GIT_REPO_FOLDER/application"
echo "@codbex:registry=https://npm.pkg.github.com
//npm.pkg.github.com/:_authToken=$GH_TOKEN" > .npmrc
npm install
rm -rf .npmrc

# build image
cd "$GIT_REPO_FOLDER/application"
docker build . --tag "$IMAGE"
```
### Run Docker image

```shell
# optionally remove the existing container with that name
docker rm -f "$CONTAINER_NAME"

docker run --name "$CONTAINER_NAME" -p 80:80 "$IMAGE"
```
