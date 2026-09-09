# CoinScan AI 🪙🔍

> **Identificação Numismática e Avaliação de Moedas Raras com Inteligência Artificial e Visão Computacional.**

O **CoinScan AI** é um aplicativo Android nativo, construído com **Kotlin** e **Jetpack Compose**, projetado para numismatas, colecionadores e entusiastas. O aplicativo utiliza visão multimodal e modelos avançados de IA para identificar peças numismáticas através da câmera ou galeria, detectar anomalias e erros de cunhagem, calcular faixas de valor de mercado e catalogar coleções pessoais de forma 100% segura e offline-first.

---

## 📱 Principais Recursos

- **Scanner Numismático com Guia de Alinhamento**:
  - Mira circular de alta precisão com retículo de enquadramento.
  - Alertas em tempo real sobre iluminação inadequada, reflexos em superfícies metálicas e foco.
  - Suporte à captura **bifacial (Anverso / Frente e Reverso / Verso)** com lembrete inteligente para garantir a máxima precisão de catalogação.

- **Inteligência Artificial & Visão Computacional**:
  - Identificação de País de origem, Denominação, Ano e Casa da Moeda.
  - Composição metálica (ouro, prata, cuproníquel, alpaca, bronze, etc.), diâmetro e peso oficial.
  - Detecção de variantes raras e erros históricos de cunhagem (reverso invertido, reverso horizontal, cunho quebrado, batida dupla, etc.).
  - Classificação do estado de conservação aparente na escala internacional e brasileira (FC, SOB, MBC, BC, R).

- **Salvaguarda contra Alucinações & Escala de Confiança Numismática**:
  - **Alta Confiança (90% – 100%)**: Identificação firme baseada em orla, efígie e grafia comprovadas.
  - **Boa Confiança (70% – 89%)**: Correspondência confiável com pequenas áreas de desgaste.
  - **Baixa Confiança (50% – 69%)**: Peça com desgaste severo ou baixa nitidez.
  - **Inconclusivo (< 50%)**: A IA nunca inventa dados; se a evidência for insuficiente, o laudo é emitido como inconclusivo acompanhado de dicas para uma nova captura.

- **Minha Coleção & Gestão Patrimonial (Room Database)**:
  - Catalogação completa com persistência local criptografada.
  - Cálculo automático em tempo real do patrimônio estimado da coleção.
  - Filtros por raridade (*Rara*, *Incomum*, *Comum*) e lista de favoritos.

- **Catálogo de Referência Integrado (Offline)**:
  - Base de dados com exemplares históricos de destaque (ex.: *1 Real 1998 Direitos Humanos*, *50 Centavos 2012 Sem o Zero*, *1 Real 1994 Reverso Invertido*, *Dobrão de 20.000 Réis 1724*, *Patacão 1818*, *Morgan Silver Dollar 1921*, entre outros).

- **Design Numismático Premium (Material 3)**:
  - Estética em tons de obsidiana (`#0A0E17`), ardósia escura (`#121826`) e ouro antigo (`#D4AF37`).
  - Navegação fluida com Jetpack Navigation Compose, cards modernos, animações suaves e contraste aprovado para acessibilidade.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem**: Kotlin 2.0+
- **Interface**: Jetpack Compose com Material Design 3 (M3)
- **Arquitetura**: MVVM + Clean Architecture desacoplada (Interface `AIService`)
- **Persistência**: Android Jetpack Room Database (SQLite local)
- **Visão & IA**: Gemini 2.5 Flash / Arquitetura modular extensível
- **Gerenciamento de Segredos**: Android Secrets Gradle Plugin & BuildConfig
- **Testes**: JUnit 4 com testes de catalogação, conversão de entidades e cálculos de confiança

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Android Studio Ladybug (2024.2+)** ou superior
- **JDK 17** ou superior configurado
- Dispositivo Android físico ou emulador com **Android 8.0 (API 26)** ou superior

### Passos:
1. Clone este repositório:
   ```bash
   git clone https://github.com/your-username/coinscan-ai.git
   cd coinscan-ai
   ```
2. Abra o projeto no Android Studio.
3. Aguarde a sincronização inicial das dependências do Gradle.

---

## 🔑 Configuração da IA

O projeto foi desenvolvido segundo as melhores práticas de segurança: **chaves de API nunca são expostas no código-fonte**.

1. Duplique o arquivo `.env.example` na raiz do projeto e renomeie-o para `.env`:
   ```bash
   cp .env.example .env
   ```
2. Abra o arquivo `.env` e insira sua chave do Google AI Studio / Gemini:
   ```env
   GEMINI_API_KEY=AIzaSyD...sua_chave_aqui...
   ```
3. O Secrets Gradle Plugin injetará automaticamente a chave na classe `BuildConfig.GEMINI_API_KEY` durante o processo de compilação.
4. Caso o aplicativo seja executado sem chave configurada, o motor de fallback de catálogo numismático garante que o aplicativo continue utilizável com análise heurística de amostra sem quebrar a experiência do usuário.

---

## 📦 Como Compilar e Gerar o APK

### Gerar APK de Debug:
No terminal da raiz do projeto, execute:
```bash
./gradlew assembleDebug
```
O arquivo APK compilado estará disponível em:
```
app/build/outputs/apk/debug/app-debug.apk
```

### Gerar APK de Release:
```bash
./gradlew assembleRelease
```

### Gerar Android App Bundle (AAB para Google Play):
```bash
./gradlew bundleRelease
```

### Executar a suíte de testes unitários:
```bash
./gradlew testDebugUnitTest
```

---

## 📂 Estrutura das Pastas

```
coinscan-ai/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/
│   │   │   │   ├── ai/               # Camada de IA, AIService, GeminiCoinAiService & ImageUtils
│   │   │   │   ├── data/             # Room Database, DAOs, Entidades e Repositórios
│   │   │   │   ├── model/            # Modelos de Domínio, Enums e Laudos Numismáticos
│   │   │   │   ├── ui/               # ViewModel, Telas Jetpack Compose, Componentes e Temas
│   │   │   │   │   ├── components/   # Badges, Cartões e Visores
│   │   │   │   │   ├── navigation/   # Rotas tipadas e NavHost
│   │   │   │   │   ├── screens/      # HomeScreen, ScannerScreen, ResultScreen, etc.
│   │   │   │   │   └── theme/        # Cores numismáticas, Tipografia e Shapes M3
│   │   │   │   └── MainActivity.kt   # Ponto de entrada nativo do aplicativo
│   │   │   ├── res/                  # Drawables, Ícone adaptativo, Strings e Layouts
│   │   │   └── AndroidManifest.xml   # Permissões e declaração de componentes
│   │   └── test/                     # Testes unitários com JUnit
│   └── build.gradle.kts              # Dependências e configurações do módulo Android
├── gradle/                           # Version Catalogs e Gradle Wrappers
├── .env.example                      # Template de variáveis de ambiente
├── .gitignore                        # Regras de exclusão de artefatos e credenciais
├── LICENSE                           # Licença MIT
└── README.md                         # Documentação completa
```

---

## 🔒 Privacidade & Segurança

- **Uso da Câmera**: Utilizada exclusivamente para a captura no momento do escaneamento. Nenhuma foto é armazenada em servidores externos sem o consentimento do usuário.
- **Armazenamento 100% Local**: O banco de dados Room reside no sandbox de armazenamento privado do aparelho.
- **Blindagem de Credenciais**: Arquivos `.env` e chaves privadas são permanentemente ignorados pelo `.gitignore`.

---

## ⚠️ Aviso Legal sobre Estimativas de Valores

> **Importante:** As estimativas de valor financeiro apresentadas pelo **CoinScan AI** são calculadas a partir de dados históricos de catálogos numismáticos, leilões e tiragens conhecidas. Elas possuem caráter estritamente informativo e **não constituem laudo pericial ou garantia de compra/venda**.
> 
> O valor real de qualquer peça numismática depende de inspeção presencial por perito qualificado, aferição de massa em balança de precisão, medição micrométrica e verificação de pátina e autenticidade.

---

## 📄 Licença

Este projeto é distribuído sob a licença [MIT](LICENSE).
