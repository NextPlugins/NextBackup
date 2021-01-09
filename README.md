# NextBackup
Um plugin para fazer backups a cada X minutos/segundos, definido por você.

# Configuração
A configuração é bem simples, apenas há um campo na `config.yml`!

```yaml
ticks-delay: 1200
```

O valor padrão é 1200 ticks (1 minuto).

A cada esse um minuto que passar, um backup ocorrerá. Claro que por conta disso o servidor poderá ter um pouco de lag, mas nada muito severo. Em nossos testes, levou apenas 2 segundos!

# Em breve
Temos em mente adicionar auto-upload dos arquivos para algum lugar definido por você.

# Tecnologias usadas
Não é necessária nenhuma plugin como dependência, todas elas são baixadas a partir do momento em que o plugin é ligado. 

### APIs e Frameworks
 * [ConfigValue](https://github.com/eikefab/ConfigValue)
