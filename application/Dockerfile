# Docker descriptor for codbex-athena
# License - http://www.eclipse.org/legal/epl-v20.html

FROM ghcr.io/codbex/codbex-gaia:2.70.0

COPY node_modules/@codbex target/dirigible/repository/root/registry/public/

ENV DIRIGIBLE_HOME_URL=/services/web/dashboard/

EXPOSE 80
