# SelecaoIoT

O servidor se encontra no package "server" do projeto eclipse contido na pasta spark-master

1 - Houve o foco em diminuir o esforço realizado pelo servidor, com intuito de aumentar a escalabilidade do mesmo

2 - A aplicação necessita do URL do servidor para funcionar, portanto o endereço IPV4 da máquina que roda o servidor na rede interna precisa ser atualizado
2.1 - Para realizar tal atualização, Aperte Windows + R, digite "ipconfig" e anote o endereço do ipv4 que aparecerá no console do CMD
2.2 - Com o numero em mãos, realizar a alteração da constante presente na Linha 27 da MainActivity.java e alterar o endereço atual, mantendo o seu IPV4 anotado concatenado com "4567/allseries/"

3  - Para Rodar a aplicação basta rodar o servidor e após isto, rodar o aplicativo. Lembrando, tenha certeza que adicionou o caminho correto ao path do item 2

