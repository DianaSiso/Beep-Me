sudo docker stop frontend_container
sudo docker rm frontend_container
sudo docker rmi frontend
sudo docker build -t frontend .
sudo docker run -p 80:80 --name frontend_container frontend
