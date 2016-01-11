package info.androidhive.materialtabs;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HP on 25.11.2015.
 */
public class Debt implements Parcelable {
    int id;
     String name;
     String number;
     String money;

    public Debt() {
    }

    public Debt(String name, String number, String money) {

        this.name = name;
        this.number = number;
        this.money = money;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", money='" + money + '\'' +
                '}';
    }

    protected Debt(Parcel in) {
        id = in.readInt();
        name = in.readString();
        number = in.readString();
        money = in.readString();
    }

    public static final Creator<Debt> CREATOR = new Creator<Debt>() {
        @Override
        public Debt createFromParcel(Parcel in) {
            return new Debt(in);
        }

        @Override
        public Debt[] newArray(int size) {
            return new Debt[size];
        }
    };

    public int getId() {
        return id++;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(number);
        dest.writeString(money);
    }
}

