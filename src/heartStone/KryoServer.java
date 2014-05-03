package heartStone;

import util.Network;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import gameObjects.Player;
import util.Network.RegisterName;
import java.io.IOException;
import java.util.ArrayList;
import util.Network.ActionMessage;
import util.Stats;

/**
 *
 * @author Enrique Martín Arenal
 * @fecha 26-abr-2014
 * @enunciado
 */
public class KryoServer {

    Server server;
    ArrayList<Connection> notYetInGame;
    ArrayList<GameConnection> players;

    public KryoServer() throws IOException {
        this.notYetInGame = new ArrayList<>();
        server = new Server() {
            protected Connection newConnection() {
                // By providing our own connection implementation, we can store per
                // connection state without a connection ID to state look up.
                return new GameConnection();
            }
        };
        // For consistency, the classes to be sent over the network are
        // registered by the same method for both the client and server.
        Network.register(server);
        server.addListener(new Listener() {
            public void received(Connection c, Object object) {
                // We know all connections for this server are actually ChatConnections.
                GameConnection connection = (GameConnection) c;

                if (object instanceof RegisterName) {
                    System.out.println("hh");
                    // Ignore the object if a client has already registered a name. This is
                    // impossible with our client, but a hacker could send messages at any time.
                    // if (connection.name != null) {
                    //      return;
                    //  }
                    // Ignore the object if the name is invalid.
                    String name = ((RegisterName) object).name;
                    if (!name.equals("Start")) {
                        return;
                    }
                    // Store the name on the connection.
                    connection.name = name;

                    //Bind the connection to an enemy if its possible
                    if (checkFreeConnections()) {
                        connection.firstTurn = false;
                        connection.enemy = getFreeConnection();
                        removeFreeConnections();
                        Connection[] connections = server.getConnections();
                        for (int i = 0; i < connections.length; i++) {
                            if (connections[i].equals(connection.enemy)) {
                                GameConnection enemy = (GameConnection) connections[i];
                                //the enemy of this player is this player himself
                                enemy.enemy = c;
                                break;
                            }
                        }
                        Network.ActionMessage sendAction = new Network.ActionMessage();
                        sendAction.action = Network.ActionMessage.START;
                        connection.enemy.sendTCP(sendAction);
                        connection.sendTCP(sendAction);

                    } else {
                        connection.firstTurn = true;
                        addFreeConnections(c);
                    }

                    return;
                }

                if (object instanceof ActionMessage) {
                    ActionMessage msg = (ActionMessage) object;
                    if (msg.action.equals(ActionMessage.START)) {
                        Player player = new Player(!connection.firstTurn);
                        sendCard((GameConnection) connection.enemy);
                        sendCard((GameConnection) connection.enemy);

                        if (connection.firstTurn) {
                            player.setCrystalsLeft(0);
                        } else {
                            player.setCrystalsLeft(1);
                        }

                        connection.enemy.sendTCP(player);
                    } else if (msg.action.equals(ActionMessage.PASS_TURN)) {
                        sendCard((GameConnection) connection.enemy);
                        connection.enemy.sendTCP(msg);
                    }
                }

                if (object instanceof Stats) {
                    Stats card = (Stats) object;
                    //if (card.getCardAction().equals(Stats.CARD_ACTION_NEW_ENEMY_CARD)) {
                    connection.enemy.sendTCP(card);
                    //} else if (card.getCardAction().equals(Stats.CARD_ACTION_ATTACKED_CARD)) {
                    //} else if (card.getCardAction().equals(Stats.CARD_ACTION_ATTACKING_CARD)) {
                    //} else if (card.getCardAction().equals(Stats.CARD_ACTION_ATTACK_PLAYER)) {
                    // }

                    return;
                }

                if (object instanceof Player) {
                    connection.enemy.sendTCP(object);
                }
            }

            public void disconnected(Connection connection) {
               
                 GameConnection c = (GameConnection) connection;
                 if(c.enemy!=null){
                     Network.ActionMessage sendAction = new Network.ActionMessage();
                     sendAction.action=ActionMessage.DISCONNECT;
                     c.enemy.sendTCP(sendAction);
                 }else{
                     notYetInGame.remove(c);
                 }
            }
        });
        server.bind(Network.port);
        server.start();

    }

    // This holds per connection state.
    static class GameConnection extends Connection {

        public String name;
        public Connection enemy;
        public ArrayList<Stats> playerCards = new ArrayList<>();
        public boolean firstTurn;

        public GameConnection() {
            setCardStats(playerCards);
        }
        //Method invoqued for initialize the cards of each player

        public void setCardStats(ArrayList<Stats> cardStats) {
            //                crystal/attack/hitpoint
            Stats stat1 = new Stats(1, 1, 2, "goldshirefootman");
            cardStats.add(stat1);
            Stats stat2 = new Stats(5, 3, 6, "fencreeper");
            cardStats.add(stat2);
            Stats stat3 = new Stats(3, 5, 1, "magmarager");
            cardStats.add(stat3);
            Stats stat4 = new Stats(2, 2, 3, "rivercrocolisk");
            cardStats.add(stat4);
            Stats stat5 = new Stats(2, 2, 2, "frostwolfgrunt");
            cardStats.add(stat5);
            Stats stat6 = new Stats(3, 3, 3, "ironfurgrizzly");
            cardStats.add(stat6);
            Stats stat7 = new Stats(2, 2, 2, "koboldgeomancer");
            cardStats.add(stat7);
            Stats stat8 = new Stats(6, 4, 7, "archmage");
            cardStats.add(stat8);
            Stats stat9 = new Stats(6, 6, 5, "lordofthearena");
            cardStats.add(stat9);
            Stats stat10 = new Stats(1, 2, 1, "murlockraider");
            cardStats.add(stat10);
            Stats stat11 = new Stats(2, 3, 2, "bloodfenraptor");
            cardStats.add(stat11);
            Stats stat12 = new Stats(2, 3, 5, "senjinshieldmast");
            cardStats.add(stat12);
            Stats stat13 = new Stats(4, 4, 5, "chillwindyeti");
            cardStats.add(stat13);
            Stats stat14 = new Stats(7, 7, 7, "wargolem");
            cardStats.add(stat14);
            Stats stat15 = new Stats(5, 5, 4, "bootybaybodyguard");
            cardStats.add(stat15);
            Stats stat16 = new Stats(4, 4, 4, "ogremagi");
            cardStats.add(stat16);
            Stats stat17 = new Stats(6, 6, 7, "boulderfistogrepng");
            cardStats.add(stat17);
            Stats stat18 = new Stats(7, 9, 5, "corehound");
            cardStats.add(stat18);
            Stats stat19 = new Stats(0, 1, 1, "wisp");
            cardStats.add(stat19);
            Stats stat20 = new Stats(1, 2, 1, "youngpriestess");
            cardStats.add(stat20);
            Stats stat21 = new Stats(1, 2, 1, "worgeninfiltrator");
            cardStats.add(stat21);
            Stats stat22 = new Stats(8, 8, 8, "ragnarosfirelord");
            cardStats.add(stat22);
            Stats stat23 = new Stats(9, 7, 9, "malygos");
            cardStats.add(stat23);
            Stats stat24 = new Stats(3, 2, 4, "dalaranmage");
            cardStats.add(stat24);
        }
    }

    synchronized boolean checkFreeConnections() {
        if (notYetInGame.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    synchronized Connection getFreeConnection() {
        return notYetInGame.get(0);
    }

    synchronized void removeFreeConnections() {
        notYetInGame.remove(0);
    }

    synchronized void addFreeConnections(Connection con) {
        notYetInGame.add(con);
    }

    //Choose a random 'card' and sends it to the correspondant player
    private void sendCard(GameConnection player) {
        int rng = (int) (Math.random() * player.playerCards.size());
        player.playerCards.get(rng).setCardAction(Stats.CARD_ACTION_NEW_CARD);
        player.sendTCP(player.playerCards.get(rng));
        player.playerCards.remove(rng);
    }

    public static void main(String[] args) throws IOException {
        Log.set(Log.LEVEL_DEBUG);
        new KryoServer();
    }
}
