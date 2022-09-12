import {useEffect, useState} from "react";
import axios from "axios";
import {UserInfo} from "../type/UserInfo";

type UserProductListProps = {
    userInfo: UserInfo | undefined,
}
export default function UserProductList(props: UserProductListProps) {

    const [myOrders, setMyOrders] = useState();

    useEffect(() => {
            if (props.userInfo)
                axios.get("/api/orders/" + props.userInfo.name)
                    .then(response => setMyOrders(response.data))
        }, [props.userInfo]
    )

    return (
        <p>
            BLA
        </p>
    )
}
