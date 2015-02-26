package metrix.connector.command;

import metrix.connector.command.api.ApiClientCommand;
import metrix.connector.command.ui.UICommand;

import com.netflix.hystrix.MetrixCommand;

/**
 * Created by chanwook on 2015. 2. 5..
 */
public enum EventType {
    UI, API, DB;
    
}
