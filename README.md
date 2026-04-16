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

### Manual Steps for Local Testing

```shell
cd "$GIT_REPO_FOLDER"
# build images
docker build -t codbex-athena:test application/
docker build -t codbex-athena-data-sample:test application-data-sample/

# run tests with image tags as Maven system properties
mvn clean install -P integration-tests \
  -DAPP_IMAGE=codbex-athena:test \
  -DSAMPLE_DATA_IMAGE=codbex-athena-data-sample:test
```

## Local Development with Docker

When running this project inside the codbex Atlas Docker image, you must provide authentication for installing dependencies from GitHub Packages.
1. Create a GitHub Personal Access Token (PAT) with `read:packages` scope.
2. Pass `NPM_TOKEN` to the Docker container:

    ```
    docker run \
    -e NPM_TOKEN=<your_github_token> \
    --rm -p 80:80 \
    ghcr.io/codbex/codbex-atlas:latest
    ```

⚠️ **Notes**
- The `NPM_TOKEN` must be available at container runtime.
- This is required even for public packages hosted on GitHub Packages.
- Never bake the token into the Docker image or commit it to source control.
