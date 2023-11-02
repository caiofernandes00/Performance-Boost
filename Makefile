export GRAALVM_HOME ~= "C:\\Users\\caiow\\apps\\graalvm-jdk-17.0.9+11.1"

########### Execute ###########
cache_performance_run:
	gradlew.bat :cache-performance:quarkusDev

.PHONY: cache_performance_run