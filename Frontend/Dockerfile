FROM python:3.8.2-alpine
WORKDIR /frontend
COPY . /frontend
EXPOSE 80
CMD [ "python3", "-m" , "http.server", "80"]

#docker build -t front-end .
#docker run -p 80:80 front-end