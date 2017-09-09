package com.shivam.tree.mousam.security_tips;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.shivam.tree.mousam.R;

public class SecurityTips extends AppCompatActivity {

    ListView listView;
    String name[] = {
            "✸ Tornado Safety Tips",
            "✸ Lightning Safety Tips",
            "✸ Flash Flood/Flood Safety Tips",
            "✸ Severe Thunderstorm Winds",
            "✸ Large Hail"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_tips);
        listView = (ListView) findViewById(R.id.securityTipLV);

        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, name);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent stAreaIn= new Intent(SecurityTips.this, SecurityTipArea.class);
                stAreaIn.putExtra("name", name[pos]);
                stAreaIn.putExtra("data", data[pos]);
                stAreaIn.putExtra("ref", ref[pos]);
                SecurityTips.this.startActivity(stAreaIn);
            }
        });


    }//onCreateEND








    //#################DATA############################################################

    String data[] = {
            "1. Seek shelter in a sturdy building, or a pre-designated shelter. Go to the lowest level of the\n" +
                    "building, preferably in a basement, and get under a heavy desk or workbench or sit next to the\n" +
                    "wall and cover your head with your arms/hands. Best bet – have a safe room in the basement.\n" +
                    "2. If an underground shelter is not available, move to an interior room/hallway – put as many\n" +
                    "wall between you and the outside of the building, and stay away from windows. Other\n" +
                    "possibilities: get into a bathtub or under a bed or sofa.\n" +
                    "3. Get out of vehicles – they can easily be tossed around – do not try to outrun a tornado.\n" +
                    "4. If caught outside – lie flat on the ground and cover your head with your hands. Remember, in\n" +
                    "tornado situations debris likes to settle in roadside ditches or other low spots. If heavy rains\n" +
                    "are falling in the area, ditches and low spots may quickly flood. Therefore, laying down in a ditch may not be your best choice.\n" +
                    "5. Be aware of flying debris – most deaths and injuries are caused by flying debris.\n" +
                    "6. Manufactured homes (mobile trailers) offer little protection, even if tied down. Leave these for a sturdy shelter before the storm\n" +
                    "approaches.\n" +
                    "7. Do not seek shelter under a highway overpass. Wind blow stronger under the overpass due to the wind-tunnel effect. Additionally,\n" +
                    "flying debris (glass, wood, metal) can pummel you, and the tornado winds may suck you out from under the overpass anyway.\n" +
                    "8. Don’t waste time opening windows and doors to equalize air pressure differences – this is a waste of time and buildings have enough\n" +
                    "air leakage to equalize air pressure differences anyway. Buildings are more likely to explode after the wind gets inside.\n" +
                    "9. The southwest side of the basement isn’t necessarily the safest place to be – vehicles can be pushed into basements – you can still be\n" +
                    "crushed no matter where you are in the basement. Even the bricks/stones of a fireplace can crash into the basement and crush you!\n" +
                    "10. Remember – the tornado can occur before there is a visible funnel cloud. A tornado is nothing more than a violently rotating column\n" +
                    "of air extending from the ground to the cloud base. You may not be able to see the tornado (can’t see the rotating air) until enough\n" +
                    "debris and dirt get swept into the vortex, and/or the visible funnel cloud develops all the way to the ground.\n" +
                    "11. No place is totally safe from tornadoes (except for a safe room) – if weather conditions come together properly, the tornado will go\n" +
                    "over or through mountains, lakes, rivers, swamps, marshes, bogs, and through downtown areas that have 1000 foot skyscrapers!",

            "1. Postpone outdoor activities if thunderstorms are imminent. Lightning can travel 5-10 miles away from the thunderstorm\n" +
                    "and strike the ground with blue sky overhead. The storm doesn’t have to be overhead in order for you to be struck.\n" +
                    "2. Move to a sturdy shelter or vehicle. Do not take shelter in a small shed, under isolated trees, or in a convertible-top\n" +
                    "vehicle. Stay away from tall objects such as trees or towers or poles.\n" +
                    "3. If in your vehicle when lightning strikes – don’t touch a metal surface. You are safer in a vehicle than being outdoors.\n" +
                    "4. Remember that utility lines or pipes can carry the electrical current underground or through a building. Avoid electrical appliances,\n" +
                    "and use telephones or computers only in an emergency.\n" +
                    "5. If you feel your hair standing on end – get down into a baseball catcher’s position and plug your ears with your finger tips so if\n" +
                    "lightning does hit it will not blow your ear drums out. Do not lie flat!\n" +
                    "6. 30/30 rule – if the time between lighting and thunder is 30 seconds or less, go to a safe shelter. Stay there until 30 minutes after the\n" +
                    "last rumble of thunder.",

            "1. Nearly half of all fatalities in a flash flood involve a person driving a vehicle. Do not drive into a flooded area – Turn\n" +
                    "Around Don’t Drown! It takes only 2 feet of water to float away most cars. It’s amazing how powerful we feel\n" +
                    "when we get behind the wheel – don’t do it!\n" +
                    "2. It takes only 6 inches of fast-moving water to sweep a person off their feet – don’t walk through a flooded area!\n" +
                    "3. If you are camping in a river valley, move to higher ground if thunderstorms with heavy rains are in the area. Do not\n" +
                    "attempt to drive away.\n" +
                    "4. Don’t operate electrical tools in flooded areas.\n" +
                    "5. Most flash flood deaths occur in the middle of the night when it is more difficult to see rising water levels judge the\n" +
                    "depth of water covering road surfaces.",

            "1. Don’t underestimate the power of strong thunderstorm winds known as straight-line winds – they can reach speeds of\n" +
                    "100 to 150 mph. Hurricane-force winds start at 74 mph. Wisconsin does experience these kinds of winds!\n" +
                    "2. If a severe thunderstorm warning contains hurricane-force wind speeds seek shelter immediately (as you would for\n" +
                    "a tornado situation).\n" +
                    "3. Stay away from windows and go to the basement or interior room/hallway. Do not use electrical appliances.\n" +
                    "4. Be aware that tall trees near a building can be uprooted by straight-line winds – that tree can come crashing through\n" +
                    "the roof of a home and crush a person to death.\n" +
                    "5. Powerful straight-line winds can overturn a vehicle or even make a person air-borne when they get up over 100 mph!\n" +
                    "6. One type of a straight-line wind event is a downburst, which is a small area of rapidly descending rain-cooled air and rain beneath a\n" +
                    "thunderstorm. A downburst can cause damage equivalent to a strong tornado!\n",

            "1. Although it is rare, people have been killed by large hail stones after sustaining head injuries. Additionally, several people are injured\n" +
                    "by large hail stones each year in the U.S.\n" +
                    "2. Some thunderstorms can produce large hail stones that can reach the size of baseballs, softballs, or even as\n" +
                    "big as computer compact discs (CD) or DVDs! These large hail stones can fall at speeds over 100 mph! –\n" +
                    "that’s why they are dangerous! The largest hail stone in Wisconsin was over 7 inches in diameter!\n" +
                    "3. If a severe storm is producing large hail stones, seek a sturdy shelter and stay away from windows that can\n" +
                    "easily be smashed.\n" +
                    "4. If you are in your vehicle before the hail storm starts, get out of it and go to a sturdy shelter. Glass windows\n" +
                    "in vehicles can easily be smashed by the hail stones. If you can’t get out of your vehicle, then come to a stop\n" +
                    "and cover your head with your arms and hands"

    };

    String ref[] = {
            "http://www.spc.noaa.gov/faq/tornado/index.html",
            "http://www.lightningsafety.noaa.gov",
            "http://www.weather.gov/os/brochures.shtml",
            "http://www.spc.noaa.gov/misc/AbtDerechos/derechofacts.htm",
            "None"
    };
}//classEND
