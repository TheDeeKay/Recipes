package co.bstorm.aleksa.recipes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.bstorm.aleksa.recipes.libs.gson.MyDeserializer;
import co.bstorm.aleksa.recipes.pojo.Component;
import co.bstorm.aleksa.recipes.pojo.Recipe;
import co.bstorm.aleksa.recipes.pojo.TagCategory;

/**
 * Created by aleksa on 7/30/16.
 *
 * Just checks if the whole parse thing works without errors
 */
public class GsonUnitTest {

    String recipeJson = "{\"status\":true,\"recipes\":{\"id\":\"2307\",\"author_id\":\"16\",\"title\":\"Kremasti doma\\u0107i sladoled od jagode i stevije\",\"en_title\":\"\",\"image_file_name\":\"https:\\/\\/tastly.net\\/uploads\\/recipe\\/original\\/recipe-2307-570280c249a6f.jpg\",\"image_size\":\"800x667\",\"difficulty\":\"2\",\"default_serving_size\":\"2\",\"default_calories\":null,\"preparation_time\":\"240\",\"utensils\":\"\",\"en_utensils\":\"\",\"description\":null,\"en_description\":null,\"blog_id\":\"15\",\"post_id\":\"0\",\"post_url\":\"\",\"likes\":\"34\",\"is_featured\":\"1\",\"is_deleted\":\"0\",\"created_at\":\"2016-04-04 14:57:06\",\"updated_at\":\"2016-04-07 13:50:57\",\"country_id\":\"184\",\"user_id\":\"5\",\"video\":null,\"is_approved\":\"1\",\"steps\":[{\"id\":\"12805\",\"text\":\"Prokuvati jagode, sok i koru narand\\u017ee dok se masa ne prepolovi. Rastvorite \\u017eelatin u malo klju\\u010dale vode i kada je pire gotov, dodajte i \\u017eelatin\",\"en_text\":\"\",\"seq_num\":\"0\",\"timer\":\"15\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12806\",\"text\":\"Izme\\u0161ajte \\u017eumanca sa mlekom i stevijom i kuvajte na pari uz stalno me\\u0161anje (najbolje \\u017eicom za me\\u0161anje) dok se ne zgusne. Kada se zgusne, ubacite i slatku pavlaku.\",\"en_text\":\"\",\"seq_num\":\"1\",\"timer\":\"15\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12807\",\"text\":\"Spojite pire od jagoda i krem od pavlake, pa ohladite kremu. Dodajte vodku ili vanilu. Probajte i korigujte zasla\\u0111iva\\u010de ako ima potrebe.\",\"en_text\":\"\",\"seq_num\":\"2\",\"timer\":\"10\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12808\",\"text\":\"Ohladite u zamrziva\\u010du u prekrivenoj ve\\u0107oj posudi dok masa ne postane polu-stegnuta. Izvadite iz zamrziva\\u010da i prome\\u0161ajte mikserom\\/varja\\u010dom. Ako vas ne mrzi uradite ovo vi\\u0161e puta.\",\"en_text\":\"\",\"seq_num\":\"3\",\"timer\":\"80\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12809\",\"text\":\"Vratite u zamrziva\\u010d i kada se potpuno stegne servirajte u staklenim \\u010da\\u0161ama uz sve\\u017ee jagode, rendanu \\u010dokoladu, blan\\u0161irane bademe ili pista\\u0107i. Mo\\u017eete da ukrasite i umu\\u0107enom slatkom pavlakom.\",\"en_text\":\"\",\"seq_num\":\"4\",\"timer\":\"120\",\"timer_name\":\"\",\"image_file_name\":null}],\"tags\":[{\"id\":\"6\"},{\"id\":\"12\"},{\"id\":\"28\"}],\"components\":[{\"id\":\"194\",\"quantity\":\"1000.00\",\"preferred_measure\":\"thousand\"},{\"id\":\"528\",\"quantity\":\"240.00\",\"preferred_measure\":\"cup\"},{\"id\":\"141\",\"quantity\":\"240.00\",\"preferred_measure\":\"cup\"},{\"id\":\"387\",\"quantity\":\"4.00\",\"preferred_measure\":\"regular\"},{\"id\":\"2022\",\"quantity\":\"0.00\",\"preferred_measure\":\"regular\"},{\"id\":\"143\",\"quantity\":\"500.00\",\"preferred_measure\":\"regular\"},{\"id\":\"497\",\"quantity\":\"1.00\",\"preferred_measure\":\"regular\"},{\"id\":\"794\",\"quantity\":\"30.00\",\"preferred_measure\":\"tbsp\"}]}}";
    String recipesJson = "{\"status\":true,\"recipes\":[{\"id\":\"2307\",\"author_id\":\"16\",\"title\":\"Kremasti domaći sladoled od jagode i stevije\",\"en_title\":\"\",\"image_file_name\":\"https://tastly.net/uploads/recipe/original/recipe-2307-570280c249a6f.jpg\",\"image_size\":\"800x667\",\"difficulty\":\"2\",\"default_serving_size\":\"2\",\"default_calories\":null,\"preparation_time\":\"240\",\"utensils\":\"\",\"en_utensils\":\"\",\"description\":null,\"en_description\":null,\"blog_id\":\"15\",\"post_id\":\"0\",\"post_url\":\"\",\"likes\":\"34\",\"is_featured\":\"1\",\"is_deleted\":\"0\",\"created_at\":\"2016-04-04 14:57:06\",\"updated_at\":\"2016-04-07 13:50:57\",\"country_id\":\"184\",\"user_id\":\"5\",\"video\":null,\"is_approved\":\"1\",\"steps\":[{\"id\":\"12805\",\"text\":\"Prokuvati jagode, sok i koru narandže dok se masa ne prepolovi. Rastvorite želatin u malo ključale vode i kada je pire gotov, dodajte i želatin\",\"en_text\":\"\",\"seq_num\":\"0\",\"timer\":\"15\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12806\",\"text\":\"Izmešajte žumanca sa mlekom i stevijom i kuvajte na pari uz stalno mešanje (najbolje žicom za mešanje) dok se ne zgusne. Kada se zgusne, ubacite i slatku pavlaku.\",\"en_text\":\"\",\"seq_num\":\"1\",\"timer\":\"15\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12807\",\"text\":\"Spojite pire od jagoda i krem od pavlake, pa ohladite kremu. Dodajte vodku ili vanilu. Probajte i korigujte zaslađivače ako ima potrebe.\",\"en_text\":\"\",\"seq_num\":\"2\",\"timer\":\"10\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12808\",\"text\":\"Ohladite u zamrzivaču u prekrivenoj većoj posudi dok masa ne postane polu-stegnuta. Izvadite iz zamrzivača i promešajte mikserom/varjačom. Ako vas ne mrzi uradite ovo više puta.\",\"en_text\":\"\",\"seq_num\":\"3\",\"timer\":\"80\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12809\",\"text\":\"Vratite u zamrzivač i kada se potpuno stegne servirajte u staklenim čašama uz sveže jagode, rendanu čokoladu, blanširane bademe ili pistaći. Možete da ukrasite i umućenom slatkom pavlakom.\",\"en_text\":\"\",\"seq_num\":\"4\",\"timer\":\"120\",\"timer_name\":\"\",\"image_file_name\":null}],\"tags\":[{\"id\":\"6\"},{\"id\":\"12\"},{\"id\":\"28\"}],\"components\":[{\"id\":\"194\",\"quantity\":\"1000.00\",\"preferred_measure\":\"thousand\"},{\"id\":\"528\",\"quantity\":\"240.00\",\"preferred_measure\":\"cup\"},{\"id\":\"141\",\"quantity\":\"240.00\",\"preferred_measure\":\"cup\"},{\"id\":\"387\",\"quantity\":\"4.00\",\"preferred_measure\":\"regular\"},{\"id\":\"2022\",\"quantity\":\"0.00\",\"preferred_measure\":\"regular\"},{\"id\":\"143\",\"quantity\":\"500.00\",\"preferred_measure\":\"regular\"},{\"id\":\"497\",\"quantity\":\"1.00\",\"preferred_measure\":\"regular\"},{\"id\":\"794\",\"quantity\":\"30.00\",\"preferred_measure\":\"tbsp\"}]},{\"id\":\"2333\",\"author_id\":\"16\",\"title\":\"Originalna Cezar salata\",\"en_title\":\"\",\"image_file_name\":\"https://tastly.net/uploads/recipe/original/recipe-2333-5704b962cc50a.jpg\",\"image_size\":\"800x532\",\"difficulty\":\"1\",\"default_serving_size\":\"1\",\"default_calories\":null,\"preparation_time\":\"75\",\"utensils\":\"\",\"en_utensils\":\"\",\"description\":null,\"en_description\":null,\"blog_id\":\"15\",\"post_id\":\"0\",\"post_url\":\"\",\"likes\":\"29\",\"is_featured\":\"1\",\"is_deleted\":\"0\",\"created_at\":\"2016-04-06 07:23:14\",\"updated_at\":\"2016-04-13 13:11:04\",\"country_id\":\"184\",\"user_id\":\"5\",\"video\":null,\"is_approved\":\"1\",\"steps\":[{\"id\":\"12924\",\"text\":\"Ispecite u rerni jedno veće belo meso pileta ili izgrilujte debele šnicle od belog mesa. Jedini začin je so i soli se pred kraj. Spremljeno pileće meso se seče na tanke, izduzene komadice (listice).\",\"en_text\":\"\",\"seq_num\":\"0\",\"timer\":\"30\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12925\",\"text\":\"secite komade crnog tost hleba na kockice.agrejte malo maslinovog ulja na srednju temperaturu i isprižite kockice uz neprestano okretanje, dok ne budu zlatne i krckave. Ovo možete da uradite i u rerni. \",\"en_text\":\"\",\"seq_num\":\"1\",\"timer\":\"10\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12926\",\"text\":\"Nekoliko listića tanko rezane pančete ispržite na tiganju bez dodavanja ulja. Trebalo bi da slanina ostane u većim i tankim komadima pri serviranju, mada se dešava da neko zbog praktičnosti isecka slaninu.\",\"en_text\":\"\",\"seq_num\":\"2\",\"timer\":\"5\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12927\",\"text\":\"Salatu oprati, dobro osušiti i iseći na trakice ili kockice. Ostaviti u frižideru 20-ak minuta da bude krckavija\",\"en_text\":\"\",\"seq_num\":\"3\",\"timer\":\"20\",\"timer_name\":\"\",\"image_file_name\":null},{\"id\":\"12928\",\"text\":\"Preliv za Cezar salatu: Koristite sastojke od majoneza pa na dole. Oljuštite beli luk i stavite u veliku činiju za salatu. Istrljajte lukom celu činiju dok ne počne da se raspada i bacite delove. Za ovu salatu dovoljno je samo malo ulja belog luka koje ostane u činiji. U istoj činiji izgnječite viljuskom inćune (ili sardine) što sitnije, dodajte majonez, senf i sok limuna, dodajte vrlo malo soli i biber i dobro umutite. Gustu masu koju ste dobili, malo razblažite maslinovim uljem i po želji – ako hoćete laganiji i ređi preliv dodajte i jogurt.\",\"en_text\":\"\",\"seq_num\":\"4\",\"timer\":\"10\",\"timer_name\":\"\",\"image_file_name\":null}],\"tags\":[{\"id\":\"5\"},{\"id\":\"10\"},{\"id\":\"16\"},{\"id\":\"21\"},{\"id\":\"22\"},{\"id\":\"27\"}],\"components\":[{\"id\":\"215\",\"quantity\":\"1.00\",\"preferred_measure\":\"regular\"},{\"id\":\"299\",\"quantity\":\"1.00\",\"preferred_measure\":\"regular\"},{\"id\":\"1066\",\"quantity\":\"0.00\",\"preferred_measure\":\"regular\"},{\"id\":\"395\",\"quantity\":\"0.00\",\"preferred_measure\":\"regular\"},{\"id\":\"192\",\"quantity\":\"60.00\",\"preferred_measure\":\"tbsp\"},{\"id\":\"206\",\"quantity\":\"0.00\",\"preferred_measure\":\"cup\"},{\"id\":\"1044\",\"quantity\":\"15.00\",\"preferred_measure\":\"tbsp\"},{\"id\":\"1047\",\"quantity\":\"3.00\",\"preferred_measure\":\"regular\"},{\"id\":\"33\",\"quantity\":\"0.00\",\"preferred_measure\":\"regular\"},{\"id\":\"12\",\"quantity\":\"2.00\",\"preferred_measure\":\"regular\"},{\"id\":\"28\",\"quantity\":\"0.00\",\"preferred_measure\":\"tbsp\"},{\"id\":\"8\",\"quantity\":\"0.00\",\"preferred_measure\":\"tsp\"},{\"id\":\"320\",\"quantity\":\"45.00\",\"preferred_measure\":\"tbsp\"}]}]}";

    String componentJson = "{\"status\":true,\"ingredients\":{\"id\":\"1\",\"name\":\"Kiseli kupus\",\"quantity_type\":\"number\"}}";
    String componentsJson = "{\"status\":true,\"ingredients\":[{\"id\":\"1\",\"name\":\"Kiseli kupus\",\"quantity_type\":\"number\"},{\"id\":\"2\",\"name\":\"Crni luk\",\"quantity_type\":\"number\"},{\"id\":\"3\",\"name\":\"Slanina\",\"quantity_type\":\"weight\"}]}";

    String tagJson = "{\"status\":true,\"tag_categories\":{\"id\":\"1\",\"name\":\"Težina spremanja\",\"tags\":[{\"id\":\"5\",\"name\":\"Lako\",\"tag_category_id\":\"1\"},{\"id\":\"6\",\"name\":\"Srednje\",\"tag_category_id\":\"1\"},{\"id\":\"7\",\"name\":\"Teško\",\"tag_category_id\":\"1\"}]}}";
    String tagsJson = "{\"status\":true,\"tag_categories\":[{\"id\":\"1\",\"name\":\"Težina spremanja\",\"tags\":[{\"id\":\"5\",\"name\":\"Lako\",\"tag_category_id\":\"1\"},{\"id\":\"6\",\"name\":\"Srednje\",\"tag_category_id\":\"1\"},{\"id\":\"7\",\"name\":\"Teško\",\"tag_category_id\":\"1\"}]},{\"id\":\"2\",\"name\":\"Vreme spremanja\",\"tags\":[{\"id\":\"8\",\"name\":\"15min\",\"tag_category_id\":\"2\"},{\"id\":\"9\",\"name\":\"30min\",\"tag_category_id\":\"2\"},{\"id\":\"10\",\"name\":\"45min\",\"tag_category_id\":\"2\"},{\"id\":\"11\",\"name\":\"1h\",\"tag_category_id\":\"2\"},{\"id\":\"12\",\"name\":\"2h+\",\"tag_category_id\":\"2\"}]},{\"id\":\"4\",\"name\":\"Tip mesa\",\"tags\":[{\"id\":\"16\",\"name\":\"Piletina\",\"tag_category_id\":\"4\"},{\"id\":\"17\",\"name\":\"Govedina\",\"tag_category_id\":\"4\"},{\"id\":\"18\",\"name\":\"Svinjetina\",\"tag_category_id\":\"4\"},{\"id\":\"19\",\"name\":\"Vegeterijanski\",\"tag_category_id\":\"4\"},{\"id\":\"29\",\"name\":\"Riba\",\"tag_category_id\":\"4\"}]},{\"id\":\"5\",\"name\":\"Prilike\",\"tags\":[{\"id\":\"20\",\"name\":\"Doručak\",\"tag_category_id\":\"5\"},{\"id\":\"21\",\"name\":\"Ručak\",\"tag_category_id\":\"5\"},{\"id\":\"22\",\"name\":\"Večera\",\"tag_category_id\":\"5\"}]},{\"id\":\"6\",\"name\":\"Način spremanja\",\"tags\":[{\"id\":\"23\",\"name\":\"Pržen\",\"tag_category_id\":\"6\"},{\"id\":\"24\",\"name\":\"Pečen\",\"tag_category_id\":\"6\"},{\"id\":\"25\",\"name\":\"Roštilj\",\"tag_category_id\":\"6\"},{\"id\":\"26\",\"name\":\"Kuvano\",\"tag_category_id\":\"6\"}]},{\"id\":\"7\",\"name\":\"Tip hrane\",\"tags\":[{\"id\":\"27\",\"name\":\"Salate\",\"tag_category_id\":\"7\"},{\"id\":\"28\",\"name\":\"Deserti\",\"tag_category_id\":\"7\"},{\"id\":\"30\",\"name\":\"Predjela\",\"tag_category_id\":\"7\"},{\"id\":\"31\",\"name\":\"Supe\",\"tag_category_id\":\"7\"},{\"id\":\"33\",\"name\":\"Jela od testa\",\"tag_category_id\":\"7\"},{\"id\":\"34\",\"name\":\"Glavna Jela\",\"tag_category_id\":\"7\"},{\"id\":\"35\",\"name\":\"Sosevi\",\"tag_category_id\":\"7\"},{\"id\":\"37\",\"name\":\"Vegan\",\"tag_category_id\":\"7\"}]}]}";

    Type recipeType = new TypeToken<ArrayList<Recipe>>(){}.getType();
    Type componentType = new TypeToken<ArrayList<Component>>(){}.getType();
    Type tagType = new TypeToken<ArrayList<TagCategory>>(){}.getType();

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(Recipe.class, new MyDeserializer<Recipe>())
            .registerTypeAdapter(recipeType, new MyDeserializer<List<Recipe>>())
            .registerTypeAdapter(Component.class, new MyDeserializer<Component>())
            .registerTypeAdapter(componentType, new MyDeserializer<List<Component>>())
            .registerTypeAdapter(TagCategory.class, new MyDeserializer<TagCategory>())
            .registerTypeAdapter(tagType, new MyDeserializer<List<TagCategory>>())
            .create();

    /**
     * Checks the Recipe parsing (both single and array)
     */
    @Test
    public void parseRecipeJsonTest(){

        Recipe recipe = gson.fromJson(recipeJson, Recipe.class);
        assert recipe != null && recipe.getTitle() != null;

        List<Recipe> recipes = gson.fromJson(recipesJson, recipeType);
        assert recipes != null && recipes.get(0) != null && recipes.get(0).getTitle() != null && recipes.size() == 2;
    }

    /**
     * Checks the Component parsing (both single and array)
     */
    @Test
    public void parseComponentJsonTest(){

        Component component = gson.fromJson(componentJson, Component.class);
        assert component != null && component.getName() != null;

        List<Component> components = gson.fromJson(componentsJson, componentType);
        assert components != null && components.get(0) != null && components.get(0).getName() != null && components.size() == 3;
    }

    /**
     * Checks the TagCategory parsing (both single and array)
     */
    @Test
    public void parseTagJsonTest(){

        TagCategory tagCategory = gson.fromJson(tagJson, TagCategory.class);
        assert tagCategory != null;

        List<TagCategory> tagCategories = gson.fromJson(tagsJson, tagType);
        assert tagCategories != null && tagCategories.get(0) != null && tagCategories.size() == 6;
    }
}
