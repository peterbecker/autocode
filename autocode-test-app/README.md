Test App
========

This module combines a Dropwizard backend and an Angular frontend into a full app,
running in a Docker container. The build requires Docker to be installed.

On running `mvn install`, this build will create a Docker image that can be run like this:

```shell
docker run --name autocode-test \
           -it \
           -p $LOCAL_PORT:8080 \
           -v $LOCAL_DIR:/data \
           peterbecker/autocode-test-docker
```

Here `LOCAL_PORT` should be set to a free port on your computer. The app will be available
on `http://localhost:$LOCAL_PORT` once it is started. 

`LOCAL_DIR` is a reference to a directory on the local machine where the data will be stored.
It needs to be absolute, prefix with `$PWD/` if it is meant to be a directory local to your
current working directory. The `-v` can be omitted, in which case the data will be ephemeral 
and will be reset on restarts.

The app can be stopped by sending SIGTERM (Ctrl+C) if `-it` was used, or always:

```shell
docker stop autocode-test
```

To clean up an old setup:
```shell
docker rm autocode-test
```