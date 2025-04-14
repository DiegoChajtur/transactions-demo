if [ -z "${DEBUG_PORT}" ]; then
  DEFAULT_DEBUG_PORT="8787"
else
  DEFAULT_DEBUG_PORT="${DEBUG_PORT}"
fi
 
if [ "${DEBUG}" = "true" ]; then
  DEBUG_OPTION=" -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=${DEFAULT_DEBUG_PORT},suspend=n,quiet=n "
fi

echo ":::::: JAVA_OPTS: ${JAVA_OPTS} "
echo ":::::: DEBUG_OPTIONS: ${DEBUG_OPTION} "

java $DEBUG_OPTION $JAVA_OPTS -jar *.jar

