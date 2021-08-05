FROM node:14-alpine

WORKDIR /usr/src/app

COPY package*.json yarn.lock ./

RUN yarn install --frozen-lockfile

COPY . .

RUN yarn build

ARG DEPLOY_PORT=5000
EXPOSE ${DEPLOY_PORT}

ENTRYPOINT [ "npx", "serve", "-s", "build" , "-l", "${DEPLOY_PORT}"]
