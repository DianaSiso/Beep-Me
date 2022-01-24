input="rest_list.txt"
while IFS= read -r line
do
    CONTAINER_NAME="data-stream-container-"
    CONTAINER_NAME+=$line
    sudo docker stop $CONTAINER_NAME
done < "$input"