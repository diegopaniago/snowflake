package snowflake.components.terminal;

import com.jediterm.terminal.TerminalColor;
import com.jediterm.terminal.TextStyle;
import com.jediterm.terminal.emulator.ColorPalette;
import com.jediterm.terminal.ui.JediTermWidget;
import com.jediterm.terminal.ui.settings.DefaultSettingsProvider;
import snowflake.common.ssh.SshUserInteraction;
import snowflake.components.main.ConnectedResource;
import snowflake.components.newsession.SessionInfo;
import snowflake.components.terminal.ssh.DisposableTtyConnector;
import snowflake.components.terminal.ssh.SshTtyConnector;

import javax.swing.*;
import java.awt.*;

public class TerminalComponent extends JPanel implements ConnectedResource {
    private JRootPane rootPane;
    private JPanel contentPane;
    private JediTermWidget term;
    private DisposableTtyConnector tty;
    private String name;

    public TerminalComponent(SessionInfo info, String name, String command) {
        setLayout(new BorderLayout());
        this.name = name;
        contentPane = new JPanel(new BorderLayout());
        rootPane = new JRootPane();
        rootPane.setContentPane(contentPane);
        add(rootPane);

        tty = new SshTtyConnector(new SshUserInteraction(info, rootPane), command);

//        Color background = new Color(40, 44, 52);
//        Color foreground = new Color(171, 178, 191);
//        Color selection = new Color(62, 68, 81);
//
//        DefaultSettingsProvider p = new DefaultSettingsProvider() {
//
//            /*
//             * (non-Javadoc)
//             *
//             * @see com.jediterm.terminal.ui.settings.DefaultSettingsProvider#
//             * getTerminalColorPalette()
//             */
//            @Override
//            public ColorPalette getTerminalColorPalette() {
//                return ColorPalette.XTERM_PALETTE;
//            }
//
//            /*
//             * (non-Javadoc)
//             *
//             * @see com.jediterm.terminal.ui.settings.DefaultSettingsProvider#
//             * useAntialiasing()
//             */
//            @Override
//            public boolean useAntialiasing() {
//                return true;
//            }
//
////			@Override
////			public boolean copyOnSelect() {
////				return true;
////			}
////
////			@Override
////			public boolean pasteOnMiddleMouseClick() {
////				return true;
////			}
//
//            @Override
//            public TextStyle getDefaultStyle() {
//                System.out.println("Default style called");
//                return new TextStyle(
//                        TerminalColor.awt(Color.WHITE),
//                        TerminalColor.awt(Color.BLACK));
//                // return new TextStyle(foreground, background)
//            }
//
//            @Override
//            public boolean emulateX11CopyPaste() {
//                return true;
//            }
//
//            @Override
//            public boolean enableMouseReporting() {
//                return true;
//            }
//
//            @Override
//            public TextStyle getFoundPatternColor() {
//                return new TextStyle(
//                        TerminalColor
//                                .awt(foreground),
//                        TerminalColor.awt(selection));
//            }
//
//            @Override
//            public TextStyle getSelectionColor() {
//                return new TextStyle(
//                        TerminalColor
//                                .awt(foreground),
//                        TerminalColor.awt(selection));
//            }
//
////			@Override
////			public Font getTerminalFont() {
////				return UIManager.getFont("Terminal.font");
////			}
//
//            @Override
//            public TextStyle getHyperlinkColor() {
//                return new TextStyle(
//                        TerminalColor
//                                .awt(foreground),
//                        TerminalColor.awt(
//                                background));
//            }
//        };

        term = new CustomJediterm(new DefaultSettingsProvider());
        term.setTtyConnector(tty);
        term.start();
        contentPane.add(term);
    }

    @Override
    public String toString() {
        return "Terminal " + this.name;
    }

    @Override
    public boolean isInitiated() {
        return true;
    }

    @Override
    public boolean isConnected() {
        return tty.isConnected();
    }

    @Override
    public void close() {
        tty.close();
    }
}
