# PROJECT_SCOPE - LeadQualBot CLI (Java)

## 1. Objetivo
- Chatbot de pré-atendimento e qualificação de leads para pequenos negócios
- Triagem inicial e encaminhamento para:
  - Orçamento
  - Agendamento
  - Atendimento humano

## 2. Entregáveis
- Aplicação Java via console (CLI) interativo
- Base de conhecimento externa em JSON
- Comandos: /ajuda, /reiniciar, /sair
- Resposta padrão (fallback) quando não houver correspondência
- README com:
  - Como executar
  - Como editar a base JSON
  - 3 roteiros de conversa: orçamento, agendamento, humano

## 3. Tecnologias e dependências
- Java 21
- Maven
- Jackson (leitura do JSON)
- Java Collections (Map, List) para estruturas internas
- Normalização de texto: java.text. Normalizer (minúsculas e remoção de acentos)
- Testes: JUnit 5 (matcher e carregamento do JSON)
- Logs básicos: SLF4J + Logback

## 4. Decisões de Arquitetura
- Layout padrão do Maven para estrutura de código e testes (src/main, src/test)
- Base de conhecimento em JSON como formato único, para suportar lista de keywords e prioridade sem convenções frágeis
- Base de conhecimento mantida fora do JAR em data/intents.json para permitir atualização sem recompilar
- Separação leve entre core e "interface":
  - Core (normalização, matching e geração de resposta) não depende de console
  - CLI atua como adaptador de entrada e saída (lê do usuário e imprime resposta)
- Logging apenas para diagnóstico básico, sem registrar dados sensíveis do usuário

## 5. Base de conhecimento (JSON)
- Arquivo externo (ex.: data/intents.json)
- Estrutura mínima por intenção:
  - intent: string
  - keywords: array de strings
  - response: string
  - priority: number

## 6. Regras de funcionamento (motor de matching)
- Fluxo:
  - ler mensagem do usuário
  - se for comando, executar comando
  - senão, normalizar texto e aplicar matching
- Normalização:
  - converter para minúsculas
  - remover acentos
  - reduzir espaços duplicados
- Matching:
  - contar ocorrências de keywords por intenção
  - selecionar a intenção com maior score
  - em empate, usar maior priority
  - persistindo empate, usar a primeira intenção definida no JSON
- Fallback:
  - se nenhum match ocorrer, responder com mensagem padrão orientando a reformular ou usar ajuda/help

## 7. Critérios de aceite (checklist)
- Executa via CLI e mantém "loop" até sair
- Comandos /ajuda, /reiniciar e /sair funcionam
- Lê a base por arquivo JSON externo (sem hardcode das intenções no código)
- Aplica normalização + matching por keywords com desempate por priority
- Retorna fallback quando não entender
- README com explicação objetiva de como executar o chatbot e contém os 3 roteiros (orçamento, agendamento, humano)
- Não quebra com entrada vazia ou caracteres inesperados

## 8. Fora de escopo
- Interface web ou GUI
- API HTTP e integrações (WhatsApp, CRM, banco de dados, Google Calendar, etc.)
- NLP/LLM/embeddings/RAG
- Persistência de histórico de conversas e armazenamento de leads

## 9. Opcional, somente se sobrar tempo (não bloqueia aceite)
- Exportar um resumo do atendimento para arquivo local (ex.: leads.txt ou leads.json) via File I/O
- Se falhar ao salvar, o chatbot continua funcionando e ainda mostra o resumo no console

