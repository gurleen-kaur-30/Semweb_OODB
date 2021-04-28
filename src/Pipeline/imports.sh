PROJECT_NAME=$1
CLASSES_DIR_1=../ProtegeGenCode/$PROJECT_NAME
CLASSES_DIR_2=../ProtegeGenCode/$PROJECT_NAME/impl

for c in $CLASSES_DIR_1/*; 
do 
    echo "Updating $c"
    sed -i "s/package $PROJECT_NAME/package ProtegeGenCode.$PROJECT_NAME/g" "$c"
    sed -i "s/import $PROJECT_NAME.impl.*/import ProtegeGenCode.$PROJECT_NAME.impl.*;/g" "$c"
done

for c in $CLASSES_DIR_2/*; 
do 
    echo "Updating $c"
    sed -i "s/package $PROJECT_NAME.impl/package ProtegeGenCode.$PROJECT_NAME.impl/g" "$c"
    sed -i "s/import $PROJECT_NAME.*/import ProtegeGenCode.$PROJECT_NAME.*;\n\nimport java.util.HashSet;\n\nimport java.io.Serializable;\nimport javax.jdo.annotations.Embedded;\nimport javax.persistence.Entity;\nimport javax.persistence.GeneratedValue;\nimport javax.persistence.Id;\n/g" "$c"
done