# Lista-De-Contatos

Prova pratica 

Estrutura usadda para construção do projeto da lista de contatos, utilizando sensores para discagem rápida.

## 🏗️ Arquitetura e Pastas

```
src/
│
├── activities/                       
│   ├── MainActivity.java            # Tela principal: lista + busca + sensor
│   ├── DetalhesActivity.java        # Exibe informações completas + ações
│   ├── EditarNumeroActivity.java    # Formulário para editar contato existente
│   └── AdicionarNumeroActivity.java # Formulário para novo contato
│
├── adapters/
│   └── ContatoAdapter.java          # RecyclerView + filtro + clique em item
│
├── db/
│   ├── ContatoDBHelper.java         # SQLiteOpenHelper: cria/atualiza esquema
│   ├── ContatoDAO.java              # CRUD completo (inserir, atualizar, deletar, listar)
│   └── ContratoContato.java         # Definição de nomes de tabela e colunas
│
├── sensors/
│   └── ProximitySensorManager.java  # Encapsula Sensor de proximidade e callback
│
├── models/
│   └── Contato.java                 # Representa o modelo de dados “Contato”
│
├── utils/
│   ├── Constants.java               # Chaves de Intent, nomes de tabelas etc.
│   └── Utilitarios.java             # Validação e formatação (ex.: e‑mail, telefone)
│
└── resources/
    ├── layout/                      # XMLs de telas e item_contato.xml
    ├── drawable/                    # Placeholders, ícones e vetores
    └── values/                      # strings.xml, colors.xml, styles.xml, dimens.xml
```
---

<div align="center">Feito com ❤️ para gerenciar seus contatos de forma rápida e prática!</div>
