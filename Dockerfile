FROM ghcr.io/navikt/baseimages/temurin:21

COPY build/libs/*.jar ./
USER root
RUN apt-get update && apt-get install -y --no-install-recommends curl