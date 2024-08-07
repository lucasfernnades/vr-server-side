# Usando a imagem oficial do PostgreSQL
FROM postgres:13

# Variáveis de ambiente para configuração do banco de dados
ENV POSTGRES_DB=vr_software_db
ENV POSTGRES_USER=vr_user
ENV POSTGRES_PASSWORD=vr_password

# Expondo a porta padrão do PostgreSQL
EXPOSE 5432
