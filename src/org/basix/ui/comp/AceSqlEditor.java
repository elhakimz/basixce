package org.basix.ui.comp;

import com.vaadin.event.ShortcutAction;
import org.vaadin.aceeditor.AceSuggestibleEditor;
import org.vaadin.aceeditor.ErrorCheckListener;
import org.vaadin.aceeditor.ErrorChecker;
import org.vaadin.aceeditor.Suggester;
import org.vaadin.aceeditor.gwt.ace.AceMode;
import org.vaadin.aceeditor.gwt.ace.AceTheme;
import org.vaadin.aceeditor.gwt.shared.Marker;
import org.vaadin.aceeditor.gwt.shared.Suggestion;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: abilhakim
 * Date: 4/2/13
 * Time: 2:14 AM
 */
public class AceSqlEditor  extends AceSuggestibleEditor {

    private static final Pattern wtfLolPlz = Pattern.compile("WTF|LOL|PLZ",
            Pattern.CASE_INSENSITIVE);


    public AceSqlEditor() {
        super();

        setTheme(AceTheme.eclipse);
        setMode(AceMode.sql);

        ShortcutAction suggestAction = new ShortcutAction("Suggest",
                ShortcutAction.KeyCode.SPACEBAR,
                new int[] { ShortcutAction.ModifierKey.CTRL });

        setSuggester(new SqlSuggester(), suggestAction);


    }

    private static class SqlErrorChecker implements ErrorChecker {
        /* @Override */
        public Collection<Marker> getErrors(String source) {
            Matcher mr = wtfLolPlz.matcher(source);
            LinkedList<Marker> errors = new LinkedList<Marker>();
            int i = 0;
            while (true) {
                if (mr.find(i)) {
                    errors.add(Marker.newErrorMarker(mr.start(), mr.end(), "'"
                            + mr.group() + "' is a banned word!"));
                    i = mr.end();
                } else {
                    break;
                }
            }
            return errors;
        }
    }

    private static class SqlSuggester implements Suggester {
        /* @Override */
        public List<Suggestion> getSuggestions(String text, int cursor) {
            LinkedList<Suggestion> suggs = new LinkedList<Suggestion>();
            suggs.add(new Suggestion("/* Hi! */"));
            suggs.add(new Suggestion("/* Moi! */"));
            suggs.add(new Suggestion("/* Hello! */"));
            return suggs;
        }
    }

    ErrorCheckListener ecl = new ErrorCheckListener(this, new SqlErrorChecker());


}
