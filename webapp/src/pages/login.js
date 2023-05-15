import {useState, useEffect} from "react";
import {request} from "../config/axios_cfg";

export const Login = () => {

    const loginData = {
        email: "",
        password:""
    }

    const [login, setLogin] = useState(loginData);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        setErrorMessage('');
    }, [login]);

    return (
        <div>

        </div>
    );

};