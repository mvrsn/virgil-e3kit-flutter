package com.virgilsecurity.e3kit

import com.virgilsecurity.android.common.exception.EThreeBaseException
import com.virgilsecurity.android.common.exception.ErrorCode
import io.flutter.plugin.common.MethodChannel

typealias FlutterError = Triple<String?, String?, Any?>

fun MethodChannel.Result.error(error: FlutterError) {
    this.error(error.first, error.second, error.third)
}

fun Throwable.defaultFlutterError(): FlutterError {
    return FlutterError(
        "unknown_error",
        message,
        null
    )
}

fun Throwable.toFlutterError(): FlutterError {
    if (this is EThreeBaseException) {
        return this.toFlutterError()
    }

    return this.defaultFlutterError()
}

fun EThreeBaseException.toFlutterError(): FlutterError {
    val key = when (this.message) {
        "Initialization of VirgilCardVerifier failed." ->
            "verifier_init_failed"
        "String to Data failed." ->
            "str_to_data_failed'"
        "Data to String failed." ->
            "str_from_data_failed'"
        "No private key on device. You should call register() of retrievePrivateKey()." ->
            "missing_private_key"
        "Passed empty FindUsersResult." ->
            "missing_public_key"
        "Passed empty array of identities to findUsers." ->
            "missing_identities"
        "User is already registered." ->
            "user_is_already_registered'"
        "User is not registered." ->
            "user_is_not_registered'"
        "Private key already exists in local key storage." ->
            "private_key_exists"
        "Verification of message failed. This may be caused by rotating sender key. Try finding new one." ->
            "verification_failed"
        "Wrong password." ->
            "wrong_password"
        "To change the password, please provide a new password that differs from the old one." ->
            "same_password"
        "Can't restore private key: private key backup has not been found." ->
            "no_private_key_backup"
        "Can't backup private key as it's already backed up." ->
            "private_key_backup_exists"
        else -> "unknown_error"
    }

    return FlutterError(
        key,
        message,
        null
    )
}
