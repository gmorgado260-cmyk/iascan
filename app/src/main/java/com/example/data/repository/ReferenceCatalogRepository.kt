package com.example.data.repository

import com.example.model.ReferenceCoin

class ReferenceCatalogRepository {

    private val catalog: List<ReferenceCoin> = listOf(
        ReferenceCoin(
            id = "br_1real_1998_dh",
            name = "1 Real 1998 - Direitos Humanos",
            country = "Brasil",
            denomination = "1 Real",
            year = "1998",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Anel de Alpaca e núcleo de Cuproníquel",
            weight = "7.84 g",
            diameter = "27.0 mm",
            rarity = "Rara",
            estimatedValueRange = "R$ 250,00 – R$ 1.800,00",
            valueMin = 250.0,
            valueMax = 1800.0,
            description = "Moeda comemorativa dos 50 Anos da Declaração Universal dos Direitos Humanos. É a mais rara e cobiçada moeda de 1 real do padrão Real circulante.",
            historicalContext = "Produzida em comemoração ao cinquentenário da declaração pela ONU. Devido à tiragem restrita de apenas 600.000 unidades, tornou-se a peça-chave para colecionadores do Plano Real.",
            visualHighlights = listOf(
                "Figura estilizada comemorativa no núcleo do anverso",
                "Legenda 'Declaração Universal dos Direitos Humanos'",
                "Bordo serrilhado intermitente bem definido",
                "Data 1998 gravada na parte inferior do anverso"
            ),
            commonVariantsOrErrors = listOf(
                "Flor de Cunho (FC) atinge valores superiores a R$ 1.800",
                "Soberba (SOB) entre R$ 450 e R$ 750",
                "Atenção para falsificações feitas com colagem de núcleo"
            ),
            category = "Moedas do Real"
        ),
        ReferenceCoin(
            id = "br_50centavos_2012_mula",
            name = "50 Centavos 2012 - Sem o Zero (Mula)",
            country = "Brasil",
            denomination = "50 Centavos (Sem Zero)",
            year = "2012",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Aço Inoxidável",
            weight = "6.80 g",
            diameter = "23.0 mm",
            rarity = "Muito Rara",
            estimatedValueRange = "R$ 1.200,00 – R$ 2.800,00",
            valueMin = 1200.0,
            valueMax = 2800.0,
            description = "Famosíssimo erro de cunhagem híbrida ('moeda mula') no qual a cunhagem do anverso da moeda de 50 centavos foi acidentalmente emparelhada com o cunho de 5 centavos, omitindo o algarismo zero.",
            historicalContext = "Um lote estimado entre 20 e 40 mil moedas escapou do controle de qualidade da Casa da Moeda em 2012 antes que a falha técnica no maquinário fosse identificada e corrigida.",
            visualHighlights = listOf(
                "Omitido o zero, exibindo visualmente apenas o número '5'",
                "Efígie do Barão do Rio Branco típica de 50 centavos no reverso",
                "Bordo liso com inscrição 'ORDEM E PROGRESSO'",
                "Espessura e massa de 50 centavos (6,8g)"
            ),
            commonVariantsOrErrors = listOf(
                "Erro oficial de cunhagem catalogado no catálogo Bentes e Amato",
                "Estado Flor de Cunho supera R$ 2.500 no mercado especializado"
            ),
            category = "Moedas com Erro"
        ),
        ReferenceCoin(
            id = "br_1real_1994_invertido",
            name = "1 Real 1994 - Reverso Invertido",
            country = "Brasil",
            denomination = "1 Real",
            year = "1994",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Aço Inoxidável (1ª Família)",
            weight = "4.27 g",
            diameter = "24.0 mm",
            rarity = "Rara",
            estimatedValueRange = "R$ 350,00 – R$ 950,00",
            valueMin = 350.0,
            valueMax = 950.0,
            description = "Moeda da 1ª família do Real com anomalia de rotação de 180 graus entre o anverso e o reverso (alinhamento medalha em vez de alinhamento moeda).",
            historicalContext = "No primeiro ano de implantação do Plano Real, a grande velocidade de cunhagem causou desalinhamento angular de cunhos em alguns discos monetários.",
            visualHighlights = listOf(
                "Giro vertical inverte a efígie da República",
                "Ano 1994 com cunhagem nítida",
                "Desenho da 1ª família com ramo de louro estilizado",
                "Bordo liso"
            ),
            commonVariantsOrErrors = listOf(
                "Reverso Invertido (180°): Mais valioso",
                "Reverso Horizontal (90°): R$ 180 a R$ 400"
            ),
            category = "Moedas com Erro"
        ),
        ReferenceCoin(
            id = "br_1real_2012_bandeira",
            name = "1 Real 2012 - Entrega da Bandeira",
            country = "Brasil",
            denomination = "1 Real",
            year = "2012",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Aço Inox e Aço revestido de Bronze",
            weight = "7.00 g",
            diameter = "27.0 mm",
            rarity = "Incomum / Cobiçada",
            estimatedValueRange = "R$ 90,00 – R$ 380,00",
            valueMin = 90.0,
            valueMax = 380.0,
            description = "Moeda comemorativa marcando a passagem da bandeira olímpica de Londres 2012 para o Rio de Janeiro 2016.",
            historicalContext = "Tiragem de 2 milhões de unidades, a menor tiragem de toda a aclamada série comemorativa dos Jogos Olímpicos Rio 2016.",
            visualHighlights = listOf(
                "Bandeira olímpica estilizada com anéis olímpicos",
                "Logotipo oficial dos Jogos Rio 2016",
                "Ano 2012 no núcleo prateado",
                "Bordo serrilhado intermitente"
            ),
            commonVariantsOrErrors = listOf(
                "Flor de Cunho em cartela oficial: R$ 350+",
                "Circulada bem conservada: R$ 90 – R$ 150"
            ),
            category = "Moedas do Real"
        ),
        ReferenceCoin(
            id = "br_1real_2014_bc",
            name = "1 Real 2014 - 50 Anos Banco Central",
            country = "Brasil",
            denomination = "1 Real",
            year = "2014",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Aço Inox e Aço revestido de Bronze",
            weight = "7.00 g",
            diameter = "27.0 mm",
            rarity = "Incomum",
            estimatedValueRange = "R$ 25,00 – R$ 140,00",
            valueMin = 25.0,
            valueMax = 140.0,
            description = "Comemorativa do cinquentenário do Banco Central do Brasil (1964–2014).",
            historicalContext = "Tiragem comemorativa autorizada pelo Conselho Monetário Nacional de 50 milhões de unidades.",
            visualHighlights = listOf(
                "Edifício-sede do Banco Central em Brasília estilizado",
                "Inscrição '50 ANOS' e logotipo do BC",
                "Datas 1964 e 2014 dispostas nas margens"
            ),
            commonVariantsOrErrors = listOf(
                "Variante de cunho duplo na legenda",
                "Flor de Cunho com brilho original: R$ 80 a R$ 140"
            ),
            category = "Moedas do Real"
        ),
        ReferenceCoin(
            id = "br_10centavos_1999_fao",
            name = "10 Centavos 1999 - Tiragem Reduzida",
            country = "Brasil",
            denomination = "10 Centavos",
            year = "1999",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Aço revestido de Bronze",
            weight = "4.80 g",
            diameter = "20.0 mm",
            rarity = "Escassa",
            estimatedValueRange = "R$ 45,00 – R$ 260,00",
            valueMin = 45.0,
            valueMax = 260.0,
            description = "Uma das menores tiragens do padrão Real para moedas de 10 centavos (apenas 3,1 milhões cunhadas).",
            historicalContext = "Em 1999, o Banco Central reduziu severamente as encomendas de moedas divisionárias devido ao estoque de anos anteriores.",
            visualHighlights = listOf(
                "Efígie de D. Pedro I no anverso",
                "Constelação do Cruzeiro do Sul no reverso",
                "Bordo serrilhado fino e uniforme"
            ),
            commonVariantsOrErrors = listOf(
                "Muito desgastada pela oxidação do bronze em circulação",
                "Exemplares sem manchas (Flor de Cunho) são muito valorizados"
            ),
            category = "Moedas do Real"
        ),
        ReferenceCoin(
            id = "br_20centavos_1970_rio_branco",
            name = "20 Centavos 1970 - Barão do Rio Branco",
            country = "Brasil",
            denomination = "20 Centavos",
            year = "1970",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Cuproníquel (75% Cobre, 25% Níquel)",
            weight = "4.12 g",
            diameter = "21.0 mm",
            rarity = "Incomum",
            estimatedValueRange = "R$ 15,00 – R$ 190,00",
            valueMin = 15.0,
            valueMax = 190.0,
            description = "Moeda clássica do padrão Cruzeiro (Novo Cruzeiro) com efígie do patrono da diplomacia brasileira.",
            historicalContext = "Peça emblemática da reforma monetária de 1967/1970 no Brasil, muito apreciada por colecionadores de moedas do século XX.",
            visualHighlights = listOf(
                "Busto em relevo nítido de José Maria da Silva Paranhos Júnior",
                "Legenda 'BRASIL' em arco superior",
                "Armas Nacionais estilizadas no reverso",
                "Bordo serrilhado clássico"
            ),
            commonVariantsOrErrors = listOf(
                "Variantes de cunho quebrado na gola do terno",
                "Exemplares Flor de Cunho certificados: R$ 150 a R$ 190"
            ),
            category = "Raridades Brasileiras"
        ),
        ReferenceCoin(
            id = "br_dobrao_1724_vila_rica",
            name = "Dobrão de 20.000 Réis 1724 - D. João V",
            country = "Brasil Colônia",
            denomination = "20.000 Réis (Dobrão)",
            year = "1724",
            mint = "Casa da Moeda de Vila Rica (Minas Gerais)",
            composition = "Ouro 917 (22 quilates)",
            weight = "53.78 g",
            diameter = "37.0 mm",
            rarity = "Extremamente Rara",
            estimatedValueRange = "R$ 55.000,00 – R$ 220.000,00",
            valueMin = 55000.0,
            valueMax = 220000.0,
            description = "A mais pesada e imponente moeda de ouro já cunhada nas Américas, símbolo do auge do Ciclo do Ouro em Minas Gerais.",
            historicalContext = "Cunhada durante o reinado de D. João V ('O Magnânimo') para circular riqueza imperial e honrar a colônia mais próspera de Portugal.",
            visualHighlights = listOf(
                "Escudo Real português coroado ladeado pelo valor 20000",
                "Cruz da Ordem de Cristo no reverso com a letra monetária 'M'",
                "Massa volumosa extraordinária superior a 53 gramas de ouro",
                "Bordo com cordão em relevo protetor contra serragem"
            ),
            commonVariantsOrErrors = listOf(
                "Data 1724/1725 com sobredata catalogada",
                "Peças em estado Soberba / Flor de Cunho batem recordes em leilões internacionais"
            ),
            category = "Império & Colônia"
        ),
        ReferenceCoin(
            id = "br_peca_coroa_1822",
            name = "Peça da Coroação de 1822 - D. Pedro I",
            country = "Brasil Império",
            denomination = "6.400 Réis",
            year = "1822",
            mint = "Casa da Moeda do Rio de Janeiro",
            composition = "Ouro 917",
            weight = "14.34 g",
            diameter = "32.0 mm",
            rarity = "Raridade Suprema Mundial",
            estimatedValueRange = "R$ 300.000,00 – R$ 2.500.000,00",
            valueMin = 300000.0,
            valueMax = 2500000.0,
            description = "A joia máxima da numismática brasileira. Apenas 64 exemplares foram cunhados para a coroação do primeiro Imperador do Brasil antes que a produção fosse suspensa.",
            historicalContext = "D. Pedro I rejeitou o desenho original porque o busto foi gravado com uniforme militar sem a coroa imperial sobre a cabeça, ordenando a imediata suspensão da emissão.",
            visualHighlights = listOf(
                "Busto descalvado e fardado de D. Pedro I",
                "Legenda 'PETRUS.I.D.G.CONST.IMP.ET.PERP.BRAS.DEF.'",
                "Brasão Imperial com ramos de café e tabaco",
                "Data 1822 e marca monetária 'R'"
            ),
            commonVariantsOrErrors = listOf(
                "Menos de 20 exemplares conhecidos em mãos de colecionadores privados",
                "Consta no acervo do Banco Central, Museu Histórico Nacional e Itaú Numismática"
            ),
            category = "Império & Colônia"
        ),
        ReferenceCoin(
            id = "br_patacao_1818",
            name = "960 Réis 'Patacão' 1818 - D. João VI",
            country = "Brasil Reino Unido",
            denomination = "960 Réis (Patacão)",
            year = "1818",
            mint = "Casa da Moeda da Bahia / Rio de Janeiro",
            composition = "Prata 896 a 903",
            weight = "27.07 g",
            diameter = "39.5 mm",
            rarity = "Histórica / Procurada",
            estimatedValueRange = "R$ 450,00 – R$ 3.800,00",
            valueMin = 450.0,
            valueMax = 3800.0,
            description = "Clássico Patacão recunhado sobre moedas de '8 Reales' coloniais espanholas da América Latina (Potosí, Lima, México).",
            historicalContext = "O Brasil recunhava moedas de prata hispano-americanas para suprir a crônica escassez de metal nobre, criando vestígios visíveis das moedas originais por baixo do relevo.",
            visualHighlights = listOf(
                "Armas do Reino Unido de Portugal, Brasil e Algarves",
                "Globo armilar sobreposto à Cruz de Cristo",
                "Vestígios visíveis ('alma') do relevo espanhol subjacente",
                "Bordo entalhado com folhas de louro"
            ),
            commonVariantsOrErrors = listOf(
                "Exemplares com data ou efígie de Carlos IV / Fernando VII visíveis valem prêmio substancial"
            ),
            category = "Império & Colônia"
        ),
        ReferenceCoin(
            id = "us_morgan_dollar_1921",
            name = "Morgan Silver Dollar 1921 - EUA",
            country = "Estados Unidos",
            denomination = "1 Dollar",
            year = "1921",
            mint = "Philadelphia / Denver / San Francisco",
            composition = "Prata 900 (90% Prata, 10% Cobre)",
            weight = "26.73 g",
            diameter = "38.1 mm",
            rarity = "Clássica Internacional",
            estimatedValueRange = "R$ 220,00 – R$ 1.500,00",
            valueMin = 220.0,
            valueMax = 1500.0,
            description = "O dólar de prata mais famoso do mundo, desenhado por George T. Morgan, celebrado pelo design clássico neoclássico da Lady Liberty.",
            historicalContext = "Ano final de cunhagem da série Morgan Dollar após interrupção desde 1904, antes da introdução do Peace Dollar.",
            visualHighlights = listOf(
                "Perfil de Lady Liberty com coroa Phrygian e flores",
                "Águia heráldica americana com asas abertas no reverso",
                "Lema 'IN GOD WE TRUST' em caracteres góticos",
                "Marca de cunho 'D', 'S' ou ausente (Philadelphia)"
            ),
            commonVariantsOrErrors = listOf(
                "Exemplares MS65+ certificados por NGC/PCGS superam R$ 1.500"
            ),
            category = "Moedas Internacionais"
        ),
        ReferenceCoin(
            id = "mc_2euro_grace_kelly_2007",
            name = "2 Euro 2007 - Princesa Grace Kelly (Mônaco)",
            country = "Mônaco",
            denomination = "2 Euro",
            year = "2007",
            mint = "Monnaie de Paris",
            composition = "Bimetálica (Níquel-Latão e Cuproníquel)",
            weight = "8.50 g",
            diameter = "25.75 mm",
            rarity = "Extremamente Rara",
            estimatedValueRange = "R$ 14.000,00 – R$ 28.000,00",
            valueMin = 14000.0,
            valueMax = 280000.0,
            description = "A moeda de 2 Euro mais cara e valiosa em circulação na zona do Euro, homenageando os 25 anos do falecimento da Princesa Grace Kelly.",
            historicalContext = "Tiragem de somente 20.001 moedas distribuídas em estojo de luxo oficial para colecionadores pelo Principado de Mônaco.",
            visualHighlights = listOf(
                "Perfil delicado da Princesa Grace de Mônaco",
                "Legenda 'MONACO' e ano 2007",
                "Marca da Casa da Moeda de Paris (Cornucópia e Trompa)",
                "Bordo gravado com inscrições e estrelas"
            ),
            commonVariantsOrErrors = listOf(
                "Todas emitidas em acabamento Proof/Brilliant Uncirculated com estojo aveludado"
            ),
            category = "Moedas Internacionais"
        )
    )

    fun getAllReferenceCoins(): List<ReferenceCoin> = catalog

    fun getCategories(): List<String> = listOf(
        "Todas",
        "Moedas do Real",
        "Moedas com Erro",
        "Raridades Brasileiras",
        "Império & Colônia",
        "Moedas Internacionais"
    )

    fun findByCategory(category: String): List<ReferenceCoin> {
        if (category == "Todas" || category.isBlank()) return catalog
        return catalog.filter { it.category.equals(category, ignoreCase = true) }
    }

    fun search(query: String): List<ReferenceCoin> {
        if (query.isBlank()) return catalog
        val q = query.trim().lowercase()
        return catalog.filter {
            it.name.lowercase().contains(q) ||
            it.country.lowercase().contains(q) ||
            it.denomination.lowercase().contains(q) ||
            it.year.contains(q) ||
            it.rarity.lowercase().contains(q) ||
            it.description.lowercase().contains(q) ||
            it.commonVariantsOrErrors.any { v -> v.lowercase().contains(q) }
        }
    }

    fun getCoinById(id: String): ReferenceCoin? = catalog.find { it.id == id }

    fun findBestMatch(country: String, denomination: String, year: String): ReferenceCoin? {
        val cNorm = country.lowercase()
        val dNorm = denomination.lowercase()
        return catalog.find {
            (it.country.lowercase().contains(cNorm) || cNorm.contains(it.country.lowercase())) &&
            (it.denomination.lowercase().contains(dNorm) || dNorm.contains(it.denomination.lowercase())) &&
            (year.isNotBlank() && it.year.contains(year))
        } ?: catalog.find {
            it.denomination.lowercase().contains(dNorm) || dNorm.contains(it.denomination.lowercase())
        }
    }
}
