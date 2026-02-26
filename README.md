# Analisador de Currículos e Vagas.AI

Um sistema completo de **Recrutamento Hibrido (IA)** moderno, desenvolvido para ler, extrair, tabular e ranquear currículos de candidatos conectando a inteligência artificial do Google Gemini.

A aplicação possui um backend robusto em **Java + Spring Boot API** e um frontend interativo em **HTML5, CSS3 e JavaScript Vanilla**, servido diretamente pela aplicação, além de persistência em banco relacional.

## 🚀 Funcionalidades

O sistema foi desenhado para facilitar e otimizar o tempo de tech recruiters através de duas ferramentas principais:

### 📊 Análise Inteligente de Perfil
- **Extração Automática:** Arraste um arquivo PDF e o sistema extrairá (via IA) os dados primordiais de contato: Nome, E-mail e Telefone.
- **Identificação de Nível:** Avaliação e sugestão automática de Senioridade (Júnior, Pleno, Sênior) baseada no peso das vivências.
- **Habilidades Sistematizadas:** Captura das dezenas de certificações e skills espalhadas no texto bruto para transformá-las em tags limpas de habilidades.
- **Resumo Executivo:** Um resumo em linguagem natural discursiva gerada pela Inteligência artificial.

### 🎯 Match do Candidato vs Vaga 
- **Upload Híbrido:** Possibilidade de anexar o CV do candidato, e no mesmo momento injetar um texto inteiro mapeando a Descrição da Vaga de Trabalho.
- **Score (0-100%):** A IA atua num modelo de classificação retornando a porcentagem de *Match* que aquele candidato possui para suprir a vaga.
- **Feedback Justificado:** O Recrutador não recebe apenas a nota fria, mas uma justificativa técnica da IA mostrando os pontos fortes aderentes e os _gaps_ do candidato frente àquela vaga em questão.

---

## 🛠 Tecnologias Utilizadas

### Backend
- **Java 17** com **Spring Boot 3.5.11**
- **Spring AI (Google GenAI)** para transacionar os prompts e extrair as respostas da Inteligência Artificial do `gemini-2.5-flash`
- **Spring Data JPA** & **Hibernate** para mapeamento objeto-relacional (ORM)
- **PostgreSQL 15** como banco de dados relacional principal
- **Swagger / OpenAPI 3** para documentação automática e testes visuais de endpoints da API
- **Apache PDFBox** para conversão e manipulação do IO de arquivos não estruturados.
- **Jakarta Security (Filter)** atuando em Header Inject para proteção simétrica via `x-api-key`.

### Frontend
- **HTML5, CSS3 e JavaScript (Vanilla)**
- Design responsivo inspirado em painéis de *Dashboard* corporativos, utilizando variáveis CSS modernas.
- Submissão assíncrona robusta processando _Multipart/Form-Data_.
- Skeleton loading dinâmico com feedbacks modais (Toasts).

---

## 🐳 Executando com Docker (Recomendado)

O projeto está totalmente configurado para rodar em containers Docker, tornando o ambiente de desenvolvimento muito mais simples e padronizado e a integração com o PostgreSQL imediata.

### Pré-requisitos
- [Docker](https://www.docker.com/) e [Docker Compose](https://docs.docker.com/compose/) instalados na sua máquina.

### Passos para rodar
Na raiz do seu projeto, inicie os modulos de orquestração via banco:

```bash
docker-compose up -d
```

Após o container do Postgres sinalizar funcionamento na porta `5432`, instancie a aplicação Spring Boot pelo maven (irá compilar as pendências e empacotar para a porta 8080 local):

```bash
# No Windows
mvnw.cmd spring-boot:run

# No Mac / Linux
./mvnw spring-boot:run
```

Após isso, acesse:
- **Painel Recruit.AI:** [http://localhost:8080](http://localhost:8080)
- **Documentação da API (Swagger):** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📚 Estrutura da API (Principais Rotas)

A documentação detalhada interativa de todas as rotas fica disponível no Swagger UI (`/swagger-ui.html`). Abaixo, um resumo da estrutura primária do Controller:

### Endpoint de Extração Clássica
- `POST /api/curriculos/analisar` - Recebe Form-Data: `file` (MultipartFile) → Retorna o Payload de `CandidatoDTO`

### Endpoint Oculto de Cross-Validation (Match)
- `POST /api/curriculos/match` - Recebe Form-Data duplo: `file` (MultipartFile) & `vaga` (String) → O payload consolida e pontua no retorno json `MatchDTO`.

*(Lembre-se: Todas as rotas base são protegidas pelo Spring Security FilterChain exigindo header autenticado).*

---

## 🔐 Variáveis de Ambiente e Configuração

O arquivo `application.properties` principal está versionado para declarar a estrutura da aplicação, porém ele atua protegido esperando a injeção nas variáveis dinâmicas `${}` para proteger o servidor. 

Para que a aplicação compile e funcione corretamente na sua máquina local, você deve criar um arquivo chamado `application-local.properties` (que já está devidamente ignorado pelo `.gitignore`) na pasta `src/main/resources/` e colar os seus segredos lá dentro:

```properties
GEMINI_API_KEY=sua_chave_do_google_ai_studio_aqui
APP_SECURITY_KEY=senha_para_header_x_api_key
```

---

## 👨💻 Tratamento de Erros e Exceções

O sistema conta com um manipulador global de exceções da arquitetura Spring (`GlobalExceptionHandler.java`), que age como contingente das requisições interceptando falhas (ex: "Exceções na Google API", "Missing File Uploads", "Chave Incompatível"). Ele unifica a apresentação destes Throwables, devolvendo de volta para o requisitante ou Frontend HTTP em um Body Formatado `.json` junto aos Status Codes (ex: `400 Bad Request` ou `503 Service Unavailable`).

---

## 📝 Licença
Desenvolvido como projeto de portfólio de engenharia para unificar ecossistemas Backend modernos com IA.

## 👤 Autor

**JOÃO LUCAS BERNARDES**

* **LinkedIn**: [https://www.linkedin.com/in/joaolucasbernardes/](https://www.linkedin.com/in/joaolucasbernardes/)
