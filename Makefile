export GRAALVM_HOME ~= "C:\\Users\\caiow\\apps\\graalvm-jdk-17.0.9+11.1"

########### CACHE APP ###########
cache_performance_run:
	gradlew.bat :cache-performance:quarkusDev

########### CACHE APP ###########
perf_run:
	bzt -o settings.artifacts-dir="artifacts" \
	    ./perf-test/cache-performance.yaml


.PHONY: cache_performance_run