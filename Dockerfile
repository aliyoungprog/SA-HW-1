FROM node:6.14.2
EXPOSE 8080
COPY Application.kt .
CMD [ "node", "Application.kt" ]
