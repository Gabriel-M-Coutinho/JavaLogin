FROM debian:bullseye

# Evitar prompts interativos
ENV DEBIAN_FRONTEND=noninteractive

# Instalar dependências necessárias
RUN apt-get update && \
    apt-get install -y curl zip unzip bash && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Instalar SDKMAN!
RUN curl -s "https://get.sdkman.io" | bash

# Disponibilizar SDKMAN! no ambiente
SHELL ["/bin/bash", "-c"]
ENV SDKMAN_DIR="/root/.sdkman"
RUN source "$SDKMAN_DIR/bin/sdkman-init.sh" && \
    sdk install java 17.0.9-tem && \
    sdk install maven && \
    sdk install gradle

# Configurar variáveis de ambiente
ENV PATH="${SDKMAN_DIR}/candidates/java/current/bin:${SDKMAN_DIR}/candidates/maven/current/bin:${SDKMAN_DIR}/candidates/gradle/current/bin:${PATH}"

# Diretório de trabalho
WORKDIR /app

# Copiar sua aplicação
COPY . /app

# Comando padrão
CMD ["java", "-version"]
