#stage 1
FROM node:latest as node
WORKDIR /app
COPY . .
RUN npm install
RUN npm install chart.js
RUN npm run build --prod --base-href /
#stage 2
FROM nginx:alpine
COPY --from=node /app/dist/beep-me-app /usr/share/nginx/html