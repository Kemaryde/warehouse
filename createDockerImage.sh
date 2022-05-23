mvn clean package
cp ./target/warehouse-*.jar ./api.jar
docker build -t registry.jlosch.de/kemary.de/warehouse:alpha-0.1 .
docker push registry.jlosch.de/kemary.de/warehouse:alpha-0.1
mvn clean