# Docker descriptor for codbex-athena
# License - http://www.eclipse.org/legal/epl-v20.html

FROM node:20 as builder

WORKDIR /app
COPY package.json  ./
RUN npm install

FROM ghcr.io/codbex/codbex-gaia:2.39.0

COPY --from=builder /app/node_modules/@codbex target/dirigible/repository/root/registry/public/

ENV DIRIGIBLE_HOME_URL=/services/web/dashboard/

