import {useEffect, useState} from "react";
import {request} from "../config/axios_cfg";

export const Register = () => {

    const registrationData = {
        userName: "",
        email: "",
        password:"",
        confirmPassword:""
    }

    const [inputData, setInputData] = useState(registrationData);
    const [errorMessage, setErrorMessage] = useState('');

    const handleInput = (event) => {
        setInputData({...inputData, [event.target.name]: event.target.value})
    };

    const handleSubmit = (event) => {

        event.preventDefault();

        request(
            'POST',
            "/owners",
            inputData
        )
            .then(response => console.log(response))
            .catch(err => console.log(err));
    };

    useEffect(() => {
        setErrorMessage('');
    }, [inputData]);

    return(
        <div>
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="userName">Username</label>
                    <input
                    type="text"
                    name="userName"
                    className="form-control"
                    autoComplete="off"
                    onChange={handleInput}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">E-mail</label>
                    <input
                        type="email"
                        name="email"
                        className="form-control"
                        autoComplete="off"
                        placeholder="name@example.com"
                        onChange={handleInput}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="password">Password</label>
                    <input
                        type="password"
                        name="password"
                        className="form-control"
                        onChange={handleInput}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="confirmPassword">Confirm password</label>
                    <input
                        type="password"
                        name="confirmPassword"
                        className="form-control"
                        onChange={handleInput}
                    />
                </div>
                <button type="submit" className="btn btn-primary">Register</button>
            </form>
        </div>
    )
};