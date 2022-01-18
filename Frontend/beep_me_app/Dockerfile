#stage 1
FROM node:lts-alpine as node
RUN npm install -g @angular/cli
WORKDIR /app
COPY . .
RUN npm install
RUN npm install chart.js
RUN ng serve
#stage 2
# FROM nginx:alpine
# COPY --from=node /app/dist/beep-me-app /usr/share/nginx/html