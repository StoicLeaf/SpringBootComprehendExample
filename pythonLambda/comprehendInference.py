import boto3, json, os

def lambda_handler(event, context):
    
    comprehendClient = boto3.client(
    'comprehend',
    aws_access_key_id=os.environ["awsIDkey"],
    aws_secret_access_key=os.environ["awsSecretKey"],
    region_name='eu-central-1'
    )
    
    print(event)
    
    detectText = json.loads(event["body"])["text"]
    
    languageResponse = comprehendClient.detect_dominant_language(Text=detectText)

    mainLanguage = languageResponse["Languages"][0]['LanguageCode']

    inferenceResponse = comprehendClient.detect_sentiment(Text=detectText, LanguageCode=mainLanguage)
    
    inferenceResponse["detectedLanguage"] = mainLanguage
    
    return {
        'statusCode': 200,
        'body': json.dumps(inferenceResponse)
    }