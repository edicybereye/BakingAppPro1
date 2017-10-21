package android.edikurniawan.me.bakingapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Edi Kurniawan on 9/18/2017.
 */

public class RecipeAbstract implements Parcelable{
    String title;
    List<Ingredient> ingredients;
    Step step;

    public RecipeAbstract() {
    }

    public RecipeAbstract(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public RecipeAbstract(Step step) {
        this.step = step;
    }

    public RecipeAbstract(String title) {
        this.title = title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ingredients);
        dest.writeParcelable(this.step, flags);
    }

    protected RecipeAbstract(Parcel in) {
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.step = in.readParcelable(Step.class.getClassLoader());
    }

    public static final Creator<RecipeAbstract> CREATOR = new Creator<RecipeAbstract>() {
        @Override
        public RecipeAbstract createFromParcel(Parcel source) {
            return new RecipeAbstract(source);
        }

        @Override
        public RecipeAbstract[] newArray(int size) {
            return new RecipeAbstract[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
